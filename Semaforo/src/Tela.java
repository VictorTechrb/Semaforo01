


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Tela extends JFrame {


    private JPanel painelDeSemaforos;
    private ControladorDeSemáforos controlador;

    public Tela(){
        super("Controlador de Semáforos");
        controlador = new ControladorDeSemáforos();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(480,480);
        this.setResizable(false);
    
    

        // Painel para os semáforos
        painelDeSemaforos = new JPanel(new GridLayout(2, 0, 10, 10));
        this.add(painelDeSemaforos, BorderLayout.CENTER);
        
        // Painel de botões
        JPanel painelBotoes = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar Semáforo");
        JButton btnRemover = new JButton("Remover Semáforo");
        JButton btnIniciar = new JButton("Iniciar Simulação");
        JButton btnPararSimulacao = new JButton("Parar Simulação");
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnIniciar);
        painelBotoes.add(btnPararSimulacao);
        this.add(painelBotoes,BorderLayout.NORTH);



        btnAdicionar.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                
        Semáforo semaforo = new Semáforo();
        painelDeSemaforos.add(semaforo);
        painelDeSemaforos.revalidate();
        painelDeSemaforos.repaint();
        controlador.inserir(); // Insere o semáforo na lista circular
        System.out.println(controlador.getQuantidade());
            }
        });

        btnRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(controlador.getQuantidade() == 0){
                    JOptionPane.showMessageDialog(null,"Não há semáforo para remover");
                }
                
                System.out.println(controlador.getQuantidade());   
            }
        });

        btnIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para iniciar a simulação
             System.out.println("Iniciar simulação");
             btnIniciar.setEnabled(false);
             btnAdicionar.setEnabled(false);
             btnRemover.setEnabled(false);
            }
        });

        btnPararSimulacao.addActionListener(new ActionListener(){

            @Override
               public void actionPerformed(ActionEvent e){
                System.out.println("Para Simulação");
                btnIniciar.setEnabled(true);
                btnAdicionar.setEnabled(true);
                btnRemover.setEnabled(true);
               
               }


        });
        


































        this.setVisible(true);
 }

    
}
