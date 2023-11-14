import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Usuario {
    private static int idAutomatico = 1;
    private int idUsuario;
    private String login;
    private String senha;

    public Usuario(String login, String senha) {
        this.idUsuario = idAutomatico++;
        this.login = login;
        this.senha = senha;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int id) {
        this.idUsuario = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public static Date getDateInput(String prompt, SimpleDateFormat sdf) {
        Date value = null;
        boolean validInput = false;
        while (!validInput) {
            String input = JOptionPane.showInputDialog(prompt);
            if (input == null) {
                return null;
            }
            try {
                value = sdf.parse(input);
                validInput = true;
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null,
                        "Formato de data inválido. Certifique-se de usar o padrão (dd/MM/yyyy)");
            }
        }
        return value;
    }

    public static boolean realizarLogin(ArrayList<Usuario> listaUsuarios) {

        String login = JOptionPane.showInputDialog("Login:");
        String senha = JOptionPane.showInputDialog("Senha:");

        for (Usuario usuario : listaUsuarios) {
            if (usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)) {
                return true; // Retorna true se o usuário foi encontrado.
            }
        }
        return false; // Retorna false se o usuário não foi encontrado.
    }

    public static void cadastrarUsuario(ArrayList<Usuario> listaUsuarios) {


        String login = JOptionPane.showInputDialog("Login:");

        // Verifica se o campo de login está vazio
        if (login == null || login.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo de login não pode estar vazio. Tente novamente.");
            return;
        }

        String senha = JOptionPane.showInputDialog("Senha:");

        // Verifica se o campo de senha está vazio
        if (senha == null || senha.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo de senha não pode estar vazio. Tente novamente.");
            return;
        }

        Usuario usuarioCadastrar = new Usuario(login, senha);

        // Adicione o novo usuário à lista.
        listaUsuarios.add(usuarioCadastrar);
        JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");

    }

    public static void editarUsuario(String[] args, ArrayList<Usuario> listaUsuarios) {
        boolean encontrado = false;
        Usuario usuarioEditar = null;

        if (listaUsuarios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum usuário cadastrado.");
        } else {
            listarUsuarios(listaUsuarios);

            String idEditarStr = JOptionPane.showInputDialog("Qual o ID do usuário que deseja editar?");
            if (idEditarStr == null || idEditarStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O ID do usuário não pode estar vazio. Tente novamente.");
            }

            try {
                int idEditar = Integer.parseInt(idEditarStr);

                for (Usuario usuario : listaUsuarios) {
                    if (usuario.getIdUsuario() == idEditar) {
                        usuarioEditar = usuario;
                        encontrado = true;
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, insira um número válido para o ID do Usuário.");
                return;
            }
            if (encontrado) {

                String[] opcoes = {"Editar Login", "Editar Senha", "Voltar"};

                int opcao = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);

                switch (opcao) {

                    case 0:    //Edição do login
                        String novoLogin = JOptionPane.showInputDialog("Edite o Login:");

                        // Verifica se o campo de login está vazio
                        if (novoLogin == null || novoLogin.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "O campo de login não pode estar vazio. Tente novamente.");
                            break;
                        }

                        usuarioEditar.setLogin(novoLogin);
                        JOptionPane.showMessageDialog(null, "Login editado!");
                        break;

                    case 1:    //Edição da senha
                        String novaSenha = JOptionPane.showInputDialog("Edite a Senha:");

                        // Verifica se o campo de senha está vazio
                        if (novaSenha == null || novaSenha.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "O campo de senha não pode estar vazio. Tente novamente.");
                            break;
                        }

                        usuarioEditar.setSenha(novaSenha);
                        JOptionPane.showMessageDialog(null, "Senha editada!");
                        break;

                    case 2:
                        break;

                    default:
                        break;

                }

            } else {
                JOptionPane.showMessageDialog(null, "Usuário não existe.");
            }
        }
    }

    //Excluir Usuário
    public static boolean excluirUsuario(int idUsuario, ArrayList<Usuario> listaUsuarios) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getIdUsuario() == idUsuario) {
                listaUsuarios.remove(usuario);
                return true; // Retorna true se o usuário foi excluído.
            }
        }
        return false; // Retorna false se o usuário não foi encontrado.
    }

    public static void listarUsuarios(ArrayList<Usuario> listaUsuarios) {

        if (listaUsuarios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum usuário cadastrado.");
        } else {
            for (Usuario usuario : listaUsuarios) {
                JOptionPane.showMessageDialog(null, "ID: " + usuario.getIdUsuario() +
                        "\nLogin: " + usuario.getLogin() +
                        "\nSenha: " + usuario.getSenha());
            }
        }
    }

    public static void emitirRelatorio(ArrayList<Servico> listaServicos) {

        if (listaServicos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum serviço realizado.");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String textoRetorno = "";
            for (Servico servico : listaServicos) {
                textoRetorno += "\nID: " + servico.getIdServico() +
                        " Cliente: " + servico.getCliente().getNome() +
                        " Equipamento: " + servico.getIdEquipamento() +
                        " Data Entrega: " + sdf.format(servico.getDataEntrega());
            }
            JOptionPane.showMessageDialog(null, textoRetorno);

        }
    }
}