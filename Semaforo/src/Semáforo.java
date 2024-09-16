import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Semáforo extends JPanel {
    private boolean estaVermelho = true;
    private boolean estaAmarelo = true;
    private boolean estaVerde = true;
    private int numeroDoSinal = 0;
    private Semáforo proximoSemaforo;

    public Semáforo() {
        this.setPreferredSize(new Dimension(100, 200));
    }

    public int getNumeroDoSinal() {
        return numeroDoSinal;
    }

    public void setNumeroDoSinal(int numeroDoSinal) {
        this.numeroDoSinal = numeroDoSinal;
    }

    public Semáforo getProximoSemaforo() {
        return proximoSemaforo;
    }

    public void setProximoSemaforo(Semáforo proximoSemaforo) {
        this.proximoSemaforo = proximoSemaforo;
    }

    public void setVermelho() {
        estaVermelho = true;
        estaAmarelo = false;
        estaVerde = false;
        repaint(); // Atualiza a interface para refletir a mudança
    }

    public void setAmarelo() {
        estaVermelho = false;
        estaAmarelo = true;
        estaVerde = false;
        repaint();
    }

    public void setVerde() {
        estaVermelho = false;
        estaAmarelo = false;
        estaVerde = true;
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Desenhar o corpo do semáforo
        g.setColor(Color.BLACK);
        g.fillRect(25, 20, 50, 200);

        // Desenhar os três círculos (vermelho, amarelo, verde)
        // Redefine o color para o padrão antes de começar a desenhar cada círculo
        g.setColor(Color.DARK_GRAY);
        g.fillOval(35, 30, 30, 30); // Círculo Vermelho
        g.fillOval(35, 90, 30, 30); // Círculo Amarelo
        g.fillOval(35, 150, 30, 30); // Círculo Verde
        
        if (estaVermelho) {
            g.setColor(Color.RED);
            g.fillOval(35, 30, 30, 30); // Vermelho
        }

        if (estaAmarelo) {
            g.setColor(Color.YELLOW);
            g.fillOval(35, 90, 30, 30); // Amarelo
        }

        if (estaVerde) {
            g.setColor(Color.GREEN);
            g.fillOval(35, 150, 30, 30); // Verde
        }
    }
        

    }

