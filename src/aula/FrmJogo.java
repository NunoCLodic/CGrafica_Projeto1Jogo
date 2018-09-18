/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aula;

import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author glauc
 */
public class FrmJogo
        extends javax.swing.JFrame
        implements Runnable {
    private int ball;
    private boolean left;
    private boolean right;
    private boolean fimJogo;
    private boolean keyRestart;
    private boolean tiro;
    private long ultimoTiro;
    private Raquete player;
    
    
    ArrayList<Base> lista = new ArrayList();
    ArrayList<Base> lixo = new ArrayList();
        
    public FrmJogo() {
        initComponents();
        createBufferStrategy(2);
        Thread t = new Thread(this);
        t.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelPainel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JOGO DE BRICK BREAKER");
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        jPanelPainel.setBackground(new java.awt.Color(204, 255, 204));
        jPanelPainel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanelPainelLayout = new javax.swing.GroupLayout(jPanelPainel);
        jPanelPainel.setLayout(jPanelPainelLayout);
        jPanelPainelLayout.setHorizontalGroup(
            jPanelPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 427, Short.MAX_VALUE)
        );
        jPanelPainelLayout.setVerticalGroup(
            jPanelPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 301, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelPainel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelPainel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
        } else if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
        } else if (evt.getKeyCode() == KeyEvent.VK_R) {
            keyRestart = true;
        }else if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            tiro = true;
        }

    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            left = false;
        } else if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
        } else if (evt.getKeyCode() == KeyEvent.VK_R) {
            keyRestart = false;
        } else if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            tiro = false;
        }

    }//GEN-LAST:event_formKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmJogo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmJogo().setVisible(true);
            }
        });
    }

@Override
public void run() {
    Graphics g;
    player = initPlayer();   
    ball = 5;
    ballCreation(ball);

    while (true) {
        player.setY(getHeight() - player.getAltura());
        g = getBufferStrategy().getDrawGraphics();
        //Limpa a tela
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, getWidth(), getHeight());

        
        //Colisao Player com Bola
        for (Base b : lista) {
            if (player.colisao(b)) {
                b.setIncY(-1);
            }
        }
        for (Base b : lista) {
            b.mover();
            b.desenhar(g);
        }
        
        //Chamada de funçoes
        checkFire();
        checkBallFire();

        for (Base b : lista) {
            for(Base c: lista){
                if(b.getClass() == Tiro.class && c.getClass() == Bola.class && b.colisao(c)){
                    lixo.add(c);
                    ball--;
                }
            }
        }

        lista.removeAll(lixo);
        lixo.clear();

        //Terminar jogo quando bola terminar
        if (ball == 0) {
            g.setColor(Color.BLACK);
            g.drawString("FIM do Jogo - Tecle R para Reiniciar", 100, 100);
            fimJogo = true;
        }
        if (fimJogo && keyRestart) {
            ball = 3;
            ballCreation(ball);
            fimJogo = false;
        }
        if (left && player.getX() > 0) {
            player.setIncX(-5);
        } else {
            if (right && player.getX() + player.largura < getWidth()) {
                player.setIncX(5);
            } else {
                player.setIncX(0);
            }
        }
        g.dispose();
        getBufferStrategy().show();
        try {
            Thread.sleep(5);
        } catch (InterruptedException ex) {
        }
    }  
} 
    
    //Crear Bola
    private void ballCreation(int balls){
        Random r = new Random();
        for (int i = 0; i < balls; i++) {
            Bola b = new Bola("img/bola.png");
            b.setX(r.nextInt(getWidth()));
            b.setY(r.nextInt(getHeight()));
            int red = r.nextInt(255);
            int green = r.nextInt(255);
            int blue = r.nextInt(255);
            b.setCor(new Color(red, green, blue));
            lista.add(b);
        }
    }
    //Crear Raquete
    private Raquete initPlayer(){
        Raquete player = new Raquete();
        player.setLargura(80);
        player.setAltura(30);
        player.setIncY(0);
        player.setIncX(0);
        player.setY(getHeight() - player.getAltura());
        lista.add(player);
        return player;
    }
    
    // conprovar o disparo.
    private void checkFire(){
        long tempo = System.currentTimeMillis();
        
        if(tiro  && tempo > ultimoTiro + 200){
            ultimoTiro = tempo;
             Tiro t = new Tiro();
             t.setIncX(0);
             t.setIncY(-2);
             t.setX(player.getX()+ player.getAltura() -3);
             t.y = player.y -2;
            t.setAltura(4);
             t.setLargura(2);
             lista.add(t);
         }
    }
    
    //Eliminar a Bola a ser atingida pelo disparo.
    private void checkBallFire(){
        for (Base b : lista) {
            Colisao aux = b.trataColisao(getWidth(), getHeight());
            if (aux == Colisao.DOWN) {
                lixo.add(b);
                ball--;
            }
            if( b.getClass() == Tiro.class && aux == Colisao.UP){
                lixo.add(b);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanelPainel;
    // End of variables declaration//GEN-END:variables
}
