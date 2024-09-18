import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Simulador extends JFrame {

    private JPanel painelDeSemaforos;
    private ControladorDeSemáforos controlador;

    public Simulador(){
        super("Controlador de Semáforos");
        controlador = new ControladorDeSemáforos();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1200,600);
        

        // Painel para os semáforos
        painelDeSemaforos = new JPanel(new GridLayout(2, 0, 10, 10));
        this.add(painelDeSemaforos, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar Semáforo");
        JButton btnInativo = new JButton("Deixar Inativo");
        JButton btnTornarAtivo = new JButton("Retirar Inatividade");
        JButton btnIniciar = new JButton("Iniciar Simulação");
        JButton btnSair = new JButton("Sair da Simulação");

        btnAdicionar.setFont(new Font("Arial",Font.PLAIN,25));
        btnInativo.setFont(new Font("Arial",Font.PLAIN,25));
        btnTornarAtivo.setFont(new Font("Arial",Font.PLAIN,25));
        btnIniciar.setFont(new Font("Arial",Font.PLAIN,25));
        btnSair.setFont(new Font("Arial",Font.PLAIN,25));

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnInativo);
        painelBotoes.add(btnIniciar);
        painelBotoes.add(btnTornarAtivo);
        painelBotoes.add(btnSair);

        
        painelBotoes.add(btnTornarAtivo);
        this.add(painelBotoes,BorderLayout.NORTH);

       btnInativo.setEnabled(false);

        btnAdicionar.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                Semáforo semaforo = new Semáforo();
                btnIniciar.setEnabled(true);
                painelDeSemaforos.add(semaforo);
                painelDeSemaforos.revalidate();
                painelDeSemaforos.repaint();
                controlador.inserir(semaforo); // Insere o semáforo na lista circular
                
            }
        });

        btnInativo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.inativo();
                painelDeSemaforos.revalidate();
                painelDeSemaforos.repaint();
                
               
             
            }
        });

        btnTornarAtivo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.tornarAtivo();
             
            }
        });

        btnIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para iniciar a simulação
                if(controlador.getQuantidadeSemaforosAtivos() <1) {
                    JOptionPane.showMessageDialog(null, "Não há nenhum semáforo ativo");
                } else {
                    controlador.iniciarSimulacao();
                    btnIniciar.setEnabled(false);
                    btnInativo.setEnabled(true);
                    
                }
            }
        });

        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
               
            }
        });

        this.setVisible(true);
    }






      
}
