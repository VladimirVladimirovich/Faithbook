package com.vladimir.inbelieffrontend.view;

import com.dtsey.inbeliefbackend.InBeliefBackend;
import com.dtsey.inbeliefbackend.data.Sex;
import com.dtsey.inbeliefbackend.data.UserLoginData;
import com.dtsey.inbeliefbackend.data.UserProfileData;
import com.dtsey.inbeliefbackend.exception.UserAlreadyRegisteredException;

import java.sql.SQLException;
import javax.swing.JOptionPane;

public class RegistrationForm extends javax.swing.JDialog {
    private boolean answer;
    private String login;
    private String password;

    public RegistrationForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    public boolean isRegisterNewUser() {
        this.setVisible(true);
        return answer;
    }
    
    public boolean checkUserProfileDataFields() {
        try {
            if( firstNameTextField.getText().isEmpty() ||
                lastNameTextField.getText().isEmpty() ||
                ageTextField.getText().isEmpty() ||
                phoneTextField.getText().isEmpty() || 
                emailTextField.getText().isEmpty() ||
                cityTextField.getText().isEmpty() ||
                aboutTextArea.getText().isEmpty() )
            {
                showWarningMessage("All fields must be completed!");
                return false;
            }
            else if (Integer.parseInt(ageTextField.getText().toString()) <= 0 || Integer.parseInt(ageTextField.getText().toString()) > 100) {
                showWarningMessage("Enter a valid age!");
                return false;
            }
            else
                return true;
        }   
        catch(NumberFormatException e) {
            showWarningMessage("Enter a valid age!");
            return false;
        }
    }
    
    public boolean registerNewUser() {
        try {
            String login = loginTextField.getText();
            String password = String.valueOf(passwordField.getPassword());
            setLoginAndPassword(login, password);
            
            String name = firstNameTextField.getText();
            String lastname = lastNameTextField.getText();
            Sex sex;
            
            if(maleRadioButton.isSelected()) { 
                sex = Sex.MALE;
            }
            else {
                sex = Sex.FEMALE;
            }
            
            int religionId = religionComboBox.getSelectedIndex() + 1;
            int age = Integer.valueOf(ageTextField.getText());
            String phone = phoneTextField.getText();
            String city = cityTextField.getText();
            String email = emailTextField.getText();
            String description = aboutTextArea.getText();

            UserProfileData userProfileData = new UserProfileData(name, lastname, sex, religionId, age, phone, city, email, description);

            boolean isRegisteredSuccessfully = InBeliefBackend.getInstance().registerNewUser(new UserLoginData(login, password), userProfileData);
                
            System.out.println("isRegisteredSuccessfully: " + isRegisteredSuccessfully);
            
            return isRegisteredSuccessfully;
        }
        catch (Exception e) {
            showWarningMessage("User with such login is already registered!");
            System.out.println("Exception while execute backend method: " + e.getMessage());
            return false;
        }
    }
    
    public void setLoginAndPassword(String login, String password) {
        this.login = login;
        this.password = password;
    }
    
    public String getLogin() {
        return login;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sexButtonGroup = new javax.swing.ButtonGroup();
        personalPanel = new javax.swing.JPanel();
        firstNameLabel = new javax.swing.JLabel();
        firstNameTextField = new javax.swing.JTextField();
        surnameLabel = new javax.swing.JLabel();
        lastNameTextField = new javax.swing.JTextField();
        religionComboBox = new javax.swing.JComboBox();
        sexLabel = new javax.swing.JLabel();
        maritalStatusLabel = new javax.swing.JLabel();
        maleRadioButton = new javax.swing.JRadioButton();
        femaleRadioButton = new javax.swing.JRadioButton();
        ageLabel = new javax.swing.JLabel();
        ageTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        phoneTextField = new javax.swing.JTextField();
        emailTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        aboutTextArea = new javax.swing.JTextArea();
        registerButton = new javax.swing.JButton();
        cancelRegisterButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cityTextField = new javax.swing.JTextField();
        loginTextField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registration form");
        setResizable(false);

        personalPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        firstNameLabel.setText("Name:"); // NOI18N

        surnameLabel.setText("Sirname:"); // NOI18N

        religionComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buddhism", "Daoism", "Catholicism", "Confucianism", "Hinduism", "Islam", "Judaism", "Pastafarianism", "Orthodox Christianity", "Protestantism", "Secular humanism" }));

        sexLabel.setText("Sex:"); // NOI18N

        maritalStatusLabel.setText("Religion:"); // NOI18N

        sexButtonGroup.add(maleRadioButton);
        maleRadioButton.setSelected(true);
        maleRadioButton.setText("male"); // NOI18N

        sexButtonGroup.add(femaleRadioButton);
        femaleRadioButton.setText("female"); // NOI18N

        ageLabel.setText("Age:"); // NOI18N

        jLabel1.setText("Phone:");

        jLabel2.setText("E-mail:");

        jLabel3.setText("About me:");

        aboutTextArea.setColumns(20);
        aboutTextArea.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        aboutTextArea.setRows(5);
        jScrollPane1.setViewportView(aboutTextArea);

        registerButton.setText("OK");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        cancelRegisterButton.setText("Cancel");
        cancelRegisterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelRegisterButtonActionPerformed(evt);
            }
        });

        jLabel4.setText("Login:");

        jLabel5.setText("Password:");

        jLabel6.setText("City:");

        javax.swing.GroupLayout personalPanelLayout = new javax.swing.GroupLayout(personalPanel);
        personalPanel.setLayout(personalPanelLayout);
        personalPanelLayout.setHorizontalGroup(
            personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(personalPanelLayout.createSequentialGroup()
                        .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(23, 23, 23)
                        .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emailTextField)
                            .addComponent(phoneTextField)))
                    .addGroup(personalPanelLayout.createSequentialGroup()
                        .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(firstNameLabel)
                            .addComponent(surnameLabel)
                            .addComponent(maritalStatusLabel)
                            .addComponent(ageLabel)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(7, 7, 7)
                        .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(religionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(firstNameTextField)
                            .addComponent(lastNameTextField)
                            .addComponent(loginTextField)
                            .addGroup(personalPanelLayout.createSequentialGroup()
                                .addComponent(maleRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(femaleRadioButton)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(passwordField)
                            .addComponent(ageTextField, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(personalPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(7, 7, 7)
                        .addComponent(jScrollPane1))
                    .addGroup(personalPanelLayout.createSequentialGroup()
                        .addComponent(registerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addComponent(cancelRegisterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(personalPanelLayout.createSequentialGroup()
                        .addComponent(sexLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(personalPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(34, 34, 34)
                        .addComponent(cityTextField)))
                .addContainerGap())
        );
        personalPanelLayout.setVerticalGroup(
            personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, personalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(loginTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNameLabel)
                    .addComponent(firstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(surnameLabel)
                    .addComponent(lastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maritalStatusLabel)
                    .addComponent(religionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ageLabel)
                    .addComponent(ageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sexLabel)
                    .addComponent(femaleRadioButton)
                    .addComponent(maleRadioButton))
                .addGap(6, 6, 6)
                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(phoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(emailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelRegisterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(registerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        maritalStatusLabel.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(personalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(personalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cancelRegisterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelRegisterButtonActionPerformed
        answer = false;
        this.dispose();
    }//GEN-LAST:event_cancelRegisterButtonActionPerformed

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        if(checkUserProfileDataFields()) {
            if(registerNewUser()) {
                answer = true;
                this.dispose();
            }
        }
    }//GEN-LAST:event_registerButtonActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RegistrationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistrationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistrationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistrationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                RegistrationForm dialog = new RegistrationForm(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea aboutTextArea;
    private javax.swing.JLabel ageLabel;
    private javax.swing.JTextField ageTextField;
    private javax.swing.JButton cancelRegisterButton;
    private javax.swing.JTextField cityTextField;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JRadioButton femaleRadioButton;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JTextField firstNameTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lastNameTextField;
    private javax.swing.JTextField loginTextField;
    private javax.swing.JRadioButton maleRadioButton;
    private javax.swing.JLabel maritalStatusLabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JPanel personalPanel;
    private javax.swing.JTextField phoneTextField;
    private javax.swing.JButton registerButton;
    private javax.swing.JComboBox religionComboBox;
    private javax.swing.ButtonGroup sexButtonGroup;
    private javax.swing.JLabel sexLabel;
    private javax.swing.JLabel surnameLabel;
    // End of variables declaration//GEN-END:variables
}
