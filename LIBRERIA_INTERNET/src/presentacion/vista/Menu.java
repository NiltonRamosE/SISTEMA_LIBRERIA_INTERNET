package presentacion.vista;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import presentacion.IMenu;
import presentacion.PresentadorMenu;
public class Menu extends javax.swing.JFrame implements IMenu{

    public Menu() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        navbarPanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        salirButton = new javax.swing.JButton();
        empleadoLabel = new javax.swing.JLabel();
        clienteLabel = new javax.swing.JLabel();
        compraLabel = new javax.swing.JLabel();
        madminLabel = new javax.swing.JLabel();
        ventaLabel = new javax.swing.JLabel();
        mclienteLabel = new javax.swing.JLabel();
        tarjetaLabel = new javax.swing.JLabel();
        productosLabel = new javax.swing.JLabel();
        perfilLabel = new javax.swing.JLabel();
        usuLabel = new javax.swing.JLabel();
        rolLabel = new javax.swing.JLabel();
        usuarioField = new javax.swing.JTextField();
        rolField = new javax.swing.JTextField();
        contentPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        navbarPanel.setBackground(new java.awt.Color(0, 204, 204));
        navbarPanel.setPreferredSize(new java.awt.Dimension(157, 550));

        jLabel7.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Bienvenido");

        salirButton.setBackground(new java.awt.Color(0, 102, 102));
        salirButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        salirButton.setForeground(new java.awt.Color(255, 255, 255));
        salirButton.setText("Salir");
        salirButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        empleadoLabel.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        empleadoLabel.setText("Empleados");

        clienteLabel.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        clienteLabel.setText("Clientes");

        compraLabel.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        compraLabel.setText("Compras");

        madminLabel.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        madminLabel.setText("Modulo Admin");

        ventaLabel.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        ventaLabel.setText("Ventas");

        mclienteLabel.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        mclienteLabel.setText("Modulo Cliente");

        tarjetaLabel.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        tarjetaLabel.setText("Tarjetas");

        productosLabel.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        productosLabel.setText("Productos");

        javax.swing.GroupLayout navbarPanelLayout = new javax.swing.GroupLayout(navbarPanel);
        navbarPanel.setLayout(navbarPanelLayout);
        navbarPanelLayout.setHorizontalGroup(
            navbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navbarPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(navbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(madminLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ventaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tarjetaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mclienteLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                    .addGroup(navbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(empleadoLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clienteLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(compraLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(productosLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
                    .addComponent(salirButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        navbarPanelLayout.setVerticalGroup(
            navbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navbarPanelLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel7)
                .addGap(40, 40, 40)
                .addComponent(empleadoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(clienteLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(compraLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(productosLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(madminLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(ventaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(mclienteLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(tarjetaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(salirButton)
                .addGap(29, 29, 29))
        );

        perfilLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/presentacion/vista/img/perfil.png")));

        usuLabel.setBackground(new java.awt.Color(255, 255, 255));
        usuLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        usuLabel.setText("Usuario:");

        rolLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        rolLabel.setText("Rol:");

        usuarioField.setEditable(false);
        usuarioField.setFocusable(false);

        rolField.setEditable(false);
        rolField.setFocusable(false);

        contentPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 204, 204), new java.awt.Color(0, 102, 102), new java.awt.Color(51, 51, 51), new java.awt.Color(51, 51, 51)));

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 549, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(navbarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(perfilLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(usuLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rolLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(usuarioField)
                            .addComponent(rolField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 574, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(perfilLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(usuarioField)
                            .addComponent(usuLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(rolField)
                            .addComponent(rolLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(navbarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel clienteLabel;
    private javax.swing.JLabel compraLabel;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JLabel empleadoLabel;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel madminLabel;
    private javax.swing.JLabel mclienteLabel;
    private javax.swing.JPanel navbarPanel;
    private javax.swing.JLabel perfilLabel;
    private javax.swing.JLabel productosLabel;
    private javax.swing.JTextField rolField;
    private javax.swing.JLabel rolLabel;
    private javax.swing.JButton salirButton;
    private javax.swing.JLabel tarjetaLabel;
    private javax.swing.JLabel usuLabel;
    private javax.swing.JTextField usuarioField;
    private javax.swing.JLabel ventaLabel;
    // End of variables declaration//GEN-END:variables

    
    @Override
    public void setPresentador(PresentadorMenu pmenu) {
        salirButton.addActionListener(pmenu);
        empleadoLabel.addMouseListener(pmenu);
        clienteLabel.addMouseListener(pmenu);
        compraLabel.addMouseListener(pmenu);
        madminLabel.addMouseListener(pmenu);
        ventaLabel.addMouseListener(pmenu);
        mclienteLabel.addMouseListener(pmenu);
        productosLabel.addMouseListener(pmenu);
        tarjetaLabel.addMouseListener(pmenu);
    }

    @Override
    public void iniciar() {
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        navbarPanel.setPreferredSize(new Dimension(165, screenSize.height));
        contentPanel.setPreferredSize(new Dimension(1185, 660));
        setVisible(true);
    }
    
    @Override
    public void cerrar(){
        this.dispose();
    }

    @Override
    public JLabel getClienteLabel() {
        return clienteLabel;
    }

    @Override
    public JLabel getCompraLabel() {
        return compraLabel;
    }

    @Override
    public JLabel getEmpleadoLabel() {
        return empleadoLabel;
    }

    @Override
    public JLabel getMadminLabel() {
        return madminLabel;
    }

    @Override
    public JLabel getMclienteLabel() {
        return mclienteLabel;
    }

    @Override
    public JLabel getVentaLabel() {
        return ventaLabel;
    }

    @Override
    public JPanel getContentPanel() {
        return contentPanel;
    }

    @Override
    public JTextField getRolField() {
        return rolField;
    }

    @Override
    public JTextField getUsuarioField() {
        return usuarioField;
    }

    @Override
    public JLabel getProductosLabel() {
        return productosLabel;
    }

    @Override
    public JLabel getTarjetaLabel() {
        return tarjetaLabel;
    }
}
