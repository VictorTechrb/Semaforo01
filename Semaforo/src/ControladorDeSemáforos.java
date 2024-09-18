import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ControladorDeSemáforos {

  private Semáforo semaforos;
  private static int quantidadeCriados;
  private List<Semáforo> listaSemaforosEmManutencao;
  private Timer timer;
  private boolean simulacaoEmAndamento = false;
  private int quantidadeSemaforosAtivos = 0;
  private Semáforo semaforoTela = this.semaforos;


  public Semáforo getSemaforos() {
    return semaforos;
  }

  public int getQuantidadeSemaforosAtivos() {
    return quantidadeSemaforosAtivos;
  }

  public void setQuantidadeSemaforosAtivos(int quantidadeSemaforosAtivos) {
    this.quantidadeSemaforosAtivos = quantidadeSemaforosAtivos;
  }

  public void setSemaforos(Semáforo semaforos) {
    this.semaforos = semaforos;

  }

  public ControladorDeSemáforos() {
    setSemaforos(null);
    setQuantidadeCriados(0);
    listaSemaforosEmManutencao = new ArrayList<>();
    
  }

  public boolean estaVazia() {

    return getSemaforos() == null;
  }

  public void inserir(Semáforo s) {
    if (estaVazia()) {

      this.semaforos = s;
      this.semaforos.setProximoSemaforo(this.semaforos);

      this.semaforos.setNumeroDoSinal(getQuantidadeCriados() + 1);
    } else {
      Semáforo sTemp = this.semaforos;

      while (sTemp.getProximoSemaforo() != this.semaforos) {

        sTemp = sTemp.getProximoSemaforo();
      }
      sTemp.setProximoSemaforo(s);
      s.setProximoSemaforo(this.semaforos);
      s.setNumeroDoSinal(getQuantidadeCriados() + 1);

    }
    setQuantidadeCriados(getQuantidadeCriados() + 1);
    setQuantidadeSemaforosAtivos(getQuantidadeSemaforosAtivos() + 1);

  }

  public void inativo() {
    if (estaVazia()) {
        JOptionPane.showMessageDialog(null, "Não há semáforo para remover");
        return;
    }

    if (getQuantidadeSemaforosAtivos() == 0) {
        JOptionPane.showMessageDialog(null, "Todos os semáforos já estão em manutenção");
        return;
    }

    Semáforo s = this.semaforos;
    List<String> numsList = new ArrayList<>();
    
    // Constrói a lista de semáforos ativos (excluindo os que estão em manutenção)
    do {
        if (s.isEmManutencao()==false) {
            numsList.add(String.valueOf(s.getNumeroDoSinal()));
        }
        s = s.getProximoSemaforo();
    } while (s != this.semaforos);

    if (numsList.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Não há semáforo ativo para colocar em Inatividade");
        return;
    }

    if (getQuantidadeSemaforosAtivos() == 1) {
        // Se houver apenas um semáforo ativo, não exibe a caixa de diálogo
        int removerSinal = Integer.parseInt(numsList.get(0));
        colocarInativo(removerSinal);
        JOptionPane.showMessageDialog(null, "O único semáforo ativo foi colocado em Inatividade.");
    } else {
        // Se houver mais de um semáforo ativo, exibe a caixa de diálogo
        String[] nums = numsList.toArray(new String[0]);

        String escolha = (String) JOptionPane.showInputDialog(null,
            "Selecione o semáforo a ser colocado em Inatividade:",
            "Colocar Semáforo em Inatividade",
            JOptionPane.QUESTION_MESSAGE,
            null,
            nums,
            nums[0]);

        if (escolha != null) {
            int removerSinal = Integer.parseInt(escolha);
            
            colocarInativo(removerSinal);
        }
    }
}
  private void colocarInativo(int removerSinal) {
    if (getQuantidadeSemaforosAtivos() == 1) {
      // Se só houver um semáforo, remover significa que não resta nenhum ativo
      setQuantidadeSemaforosAtivos(0);
      listaSemaforosEmManutencao.add(this.semaforos);
      semaforoTela.setEmManutencao(true);
      this.semaforos = null;
      return;
    }
    if (getQuantidadeSemaforosAtivos() == 0) {
      JOptionPane.showMessageDialog(null, "Todos os semáforos já estão em manutenção");
      pararSimulacao(); // Adiciona a chamada para parar a simulação
      return;
    }

    Semáforo s = this.semaforos;
    Semáforo anterior = null;

    // Caso o semáforo a ser removido seja o primeiro da lista
    if (this.semaforos.getNumeroDoSinal() == removerSinal) {
      // Encontrar o último semáforo para manter a circularidade
      while (s.getProximoSemaforo() != this.semaforos) {
        s = s.getProximoSemaforo();
      }
      // O último semáforo agora aponta para o próximo após o removido
      s.setProximoSemaforo(this.semaforos.getProximoSemaforo());
      // Atualiza a referência do início da lista
      listaSemaforosEmManutencao.add(this.semaforos);
      this.semaforos.setEmManutencao(true); // Marcar o semáforo atual como em manutenção
      this.semaforos = this.semaforos.getProximoSemaforo();
    } else {
      // Percorre a lista para encontrar o semáforo a ser removido
      do {
        anterior = s;
        s = s.getProximoSemaforo();
      } while (s.getNumeroDoSinal() != removerSinal && s != this.semaforos);

      // Se encontrou o semáforo, remove da lista
      if (s.getNumeroDoSinal() == removerSinal) {
        anterior.setProximoSemaforo(s.getProximoSemaforo());
        listaSemaforosEmManutencao.add(s);
        s.setEmManutencao(true); // Marcar o semáforo encontrado como em manutenção
      }
    }

    setQuantidadeSemaforosAtivos(getQuantidadeSemaforosAtivos() - 1);
  }

  public int getQuantidadeCriados() {
    return quantidadeCriados;
  }

  public void setQuantidadeCriados(int q) {
    quantidadeCriados = q;
  }

  public String lista() {
    if (this.semaforos == null) {
      return "Nenhum Semáforo";
    }
    String r = "[";

    Semáforo s = this.semaforos;
    do {
      if (s == null) {
        return r;
      }
      r += s.getNumeroDoSinal();
      s = s.getProximoSemaforo();
      if (s != this.semaforos) {
        r += ",";
      }

    } while (s != this.semaforos);
    r += "]";

    return r;
  }

  public void tornarAtivo() {
    // Lista de semáforos em manutenção
    if (listaSemaforosEmManutencao.size() == 0) {
        JOptionPane.showMessageDialog(null, "Não há nenhum semáforo inativo");
        return;
    }

    String[] numerosRetirarManutencao = new String[listaSemaforosEmManutencao.size()];
    for (int i = 0; i < listaSemaforosEmManutencao.size(); i++) {
        numerosRetirarManutencao[i] = String.valueOf(listaSemaforosEmManutencao.get(i).getNumeroDoSinal());
    }

    // Abre um diálogo para o usuário escolher o semáforo
    String escolhido = (String) JOptionPane.showInputDialog(null, 
        "Escolha o semáforo para retirar da manutenção", 
        "Retirar da Manutenção", 
        JOptionPane.QUESTION_MESSAGE, 
        null, 
        numerosRetirarManutencao, 
        numerosRetirarManutencao[0]);
        Semáforo novoSemaforo = new Semáforo();
    
    if (escolhido != null) {
        int n = Integer.parseInt(escolhido);
        for (int i = 0; i < listaSemaforosEmManutencao.size(); i++) {
            if (listaSemaforosEmManutencao.get(i).getNumeroDoSinal() == n) {
              novoSemaforo =listaSemaforosEmManutencao.get(i);
                listaSemaforosEmManutencao.remove(i); // Remove o semáforo da lista de manutenção
                break;
            }
        }

      // Atualizar o semáforo para ativo
      novoSemaforo.setEmManutencao(false);
       
    
        
        // Reinserir o semáforo na lista circular
        inserirInativo(novoSemaforo); 
        
         
        
       
    }
}

// Método de inserção de semáforo
public void inserirInativo(Semáforo novoSemaforo) {
  if (estaVazia()) {
      // Se a lista estiver vazia, o novo semáforo se torna o primeiro e aponta para si mesmo
      semaforos = novoSemaforo;
      novoSemaforo.setProximoSemaforo(semaforos);
      novoSemaforo.setEmManutencao(false);
  } else {
      Semáforo atual = semaforos;
      Semáforo anterior = null;

      // Encontrar a posição correta para o novo semáforo
      do {
          anterior = atual;
          atual = atual.getProximoSemaforo();
      } while (atual != semaforos && atual.getNumeroDoSinal() < novoSemaforo.getNumeroDoSinal());

      if (atual == semaforos && novoSemaforo.getNumeroDoSinal() < semaforos.getNumeroDoSinal()) {
          // Inserir no início da lista
          novoSemaforo.setProximoSemaforo(semaforos);
          anterior.setProximoSemaforo(novoSemaforo);
          semaforos = novoSemaforo;
      } else {
          // Inserir entre anterior e atual, ou no final da lista
          novoSemaforo.setProximoSemaforo(atual);
          anterior.setProximoSemaforo(novoSemaforo);
      }
      novoSemaforo.setEmManutencao(false);
  }
  setQuantidadeSemaforosAtivos(getQuantidadeSemaforosAtivos()+1);
}













public void iniciarSimulacao() {
    if (estaVazia() || simulacaoEmAndamento) {
      return;
    }

    simulacaoEmAndamento = true;

    // Inicializa semaforoTela com o primeiro semáforo da lista circular
    semaforoTela = semaforos;

    // Configura todos os semáforos para vermelho no início (opcional)
    Semáforo temp = semaforos;
    do {
      temp.setVermelho();
      temp = temp.getProximoSemaforo();
    } while (temp != semaforos);

    // Inicia a transição de cores
    timer = new Timer();
    transitarCores();
  }

  private void transitarCores() {
    if (semaforoTela == null) {
      return; // Se semáforoTela for nulo, não faz nada
    }
   

    // Transitar cores apenas para semáforos não em manutenção
    if (!semaforoTela.isEmManutencao()) {
      semaforoTela.setVerde();

      timer.schedule(new TimerTask() {
        @Override
        public void run() {
          if (!semaforoTela.isEmManutencao()) {
            semaforoTela.setAmarelo();

            timer.schedule(new TimerTask() {
              @Override
              public void run() {
                if (!semaforoTela.isEmManutencao()) {
                  semaforoTela.setVermelho();

                  timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                      // Move para o próximo semáforo na lista circular
                      semaforoTela = obterProximoSemaforo();
                      transitarCores(); // Recomeça o ciclo para o próximo semáforo
                    }
                  }, 500); // Tempo em vermelho
                } else {
                  // Caso o semáforo esteja em manutenção, move para o próximo semáforo
                  semaforoTela = obterProximoSemaforo();
                  transitarCores(); // Recomeça o ciclo para o próximo semáforo
                }
              }
            }, 500); // Tempo em amarelo
          } else {
            // Caso o semáforo esteja em manutenção, move para o próximo semáforo
            semaforoTela = obterProximoSemaforo();
            transitarCores(); // Recomeça o ciclo para o próximo semáforo
          }
        }
      }, 500); // Tempo em verde
    } else {
      // Caso o semáforo esteja em manutenção, move para o próximo semáforo
      semaforoTela = obterProximoSemaforo();
      transitarCores(); // Recomeça o ciclo para o próximo semáforo
    }
  }

  private Semáforo obterProximoSemaforo() {
    Semáforo proximo = semaforoTela.getProximoSemaforo();
    // Continua procurando o próximo semáforo que não esteja em manutenção
    while (proximo != semaforos && proximo.isEmManutencao() == true) {
      proximo = proximo.getProximoSemaforo();
    }
    return proximo;
  }

  public void pararSimulacao() {
    if (timer != null) {
      timer.cancel(); // Cancela todas as tarefas agendadas no timer
      timer.purge(); // Remove todas as tarefas canceladas da fila
      timer = null; // Define o timer como nulo
    }
    simulacaoEmAndamento = false; // Atualiza o estado da simulação
  }
}
