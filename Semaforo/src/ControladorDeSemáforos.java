public class ControladorDeSemáforos {
   
    private Semáforo semaforos;
    private static int quantidade = 0;
    

    public Semáforo getSemaforos() {
        return semaforos;
    }

    public void setSemaforos(Semáforo semaforos) {
        this.semaforos = semaforos;
    }

  public ControladorDeSemáforos(){
    setSemaforos(null);
  }

  public boolean estaVazia(){


    return getSemaforos() == null;
  }

  public void inserir(){
   
    

    if(estaVazia()){
     Semáforo s = new Semáforo();
        this.semaforos = s;
        this.semaforos.setNumeroDoSinal(1);
    this.semaforos.setProximoSemaforo(this.semaforos);
    this.setQuantidade(this.getQuantidade()+1);
    }else{
        Semáforo s;
        
        Semáforo novoSemaforo = new Semáforo();
           
        s = this.semaforos;
        
        while(s.getProximoSemaforo() != this.semaforos){
            
            s =s.getProximoSemaforo();
        }

       
        s.setProximoSemaforo(novoSemaforo);
        novoSemaforo.setProximoSemaforo(this.semaforos);
        novoSemaforo.setNumeroDoSinal(s.getNumeroDoSinal()+1);
        this.setQuantidade(this.getQuantidade()+1);
       
        

    }

  }

 
    public void remover() {
      if (estaVazia()) {
          return;
      }
      
      if (this.semaforos.getProximoSemaforo() == this.semaforos) {
          this.semaforos = null;
      } else {
          Semáforo s = this.semaforos;
          Semáforo sAnt = null;
  
          // Encontrar o último semáforo
          while (s.getProximoSemaforo() != this.semaforos) {
              sAnt = s;
              s = s.getProximoSemaforo();
          }
  
          // Ajustar o próximo do penúltimo semáforo para o primeiro
          if (sAnt != null) {
              sAnt.setProximoSemaforo(this.semaforos);
          }
      }
  
      // Atualizar a quantidade diretamente
      quantidade--;
  }
  

  public int getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(int q) {
    quantidade = q;
  }
  
    
}
