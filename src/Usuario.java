import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Usuario {
    private static int idAutomatico = 1;
    private int idUsuario;
    private String login;
    private String senha;

    public Usuario(int IdUsuario, String login, String senha) {
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

        String idUsuarioStr = JOptionPane.showInputDialog("ID do Usuário:");

        // Verifica se o usuário cancelou a entrada
        if (idUsuarioStr == null) {
            return;
        }

        // Tenta converter a string para um número
        try {
            int idUsuario = Integer.parseInt(idUsuarioStr);

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

            Usuario usuarioCadastrar = new Usuario(idUsuario, login, senha);

            // Adicione o novo usuário à lista.
            listaUsuarios.add(usuarioCadastrar);
            JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, insira um número válido para o ID do Usuário.");
        }

    }

    public static void editarUsuario(String[] args, ArrayList<Usuario> listaUsuarios) {
        boolean encontrado = false;
        Usuario usuarioEditar = null;

        if (listaUsuarios.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum usuário cadastrado.");
        } else {
            listarUsuarios(listaUsuarios);
            int idEditar = Integer.parseInt(JOptionPane.showInputDialog("Qual o ID do usuário que deseja editar?"));
            if (idEditar == -1) {
                return;
            }
            for (Usuario usuario : listaUsuarios) {
                if (usuario.getIdUsuario() == idEditar) {
                    usuarioEditar = usuario;
                    encontrado = true;
                    break;
                }
            }
            if (encontrado) {

                String[] opcoes = {"Editar ID do Usuário", "Editar Login", "Editar Senha", "Voltar"};

                int opcao = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);

                switch (opcao) {

                    case 0:// Edição do ID do usuário
                        String novoIdUsuarioStr = JOptionPane.showInputDialog("Digite o novo ID do Usuário:");
                        if (novoIdUsuarioStr == null) {
                            break;
                        }
                        try {
                            int novoIdUsuario = Integer.parseInt(novoIdUsuarioStr);
                            usuarioEditar.setIdUsuario(novoIdUsuario);
                            JOptionPane.showMessageDialog(null, "ID de usuário editado!");
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Por favor, insira um número válido para o ID do Usuário.");
                        }
                        break;

                    case 1:    //Edição do login
                        String novoLogin = JOptionPane.showInputDialog("Edite o Login:");

                        // Verifica se o campo de login está vazio
                        if (novoLogin == null || novoLogin.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "O campo de login não pode estar vazio. Tente novamente.");
                            break;
                        }

                        usuarioEditar.setLogin(novoLogin);
                        JOptionPane.showMessageDialog(null, "Login editado!");
                        break;

                    case 2:    //Edição da senha
                        String novaSenha = JOptionPane.showInputDialog("Edite a Senha:");

                        // Verifica se o campo de senha está vazio
                        if (novaSenha == null || novaSenha.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "O campo de senha não pode estar vazio. Tente novamente.");
                            break;
                        }

                        usuarioEditar.setSenha(novaSenha);
                        JOptionPane.showMessageDialog(null, "Senha editada!");
                        break;

                    case 3:
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

            Date dataInicial = getDateInput("Data Inicial (dd/MM/yyyy):", sdf);
            if (dataInicial == null) {
                return;
            }
            // Cadastro da data final
            Date dataFinal = getDateInput("Data Final (dd/MM/yyyy):", sdf);
            if (dataFinal == null) {
                return;
            }
            List<Servico> listaFiltrada = listaServicos.stream()
                    .filter(e -> (e.getDataEntrega().before(dataFinal) || e.getDataEntrega().equals(dataFinal))
                            && e.getDataEntrega().after(dataInicial) || e.getDataEntrega().equals(dataInicial))
                    .collect(Collectors.toList());

            if (listaFiltrada.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum serviço realizado.");
            } else {
                String textoRetorno = "";
                for (Servico servico : listaFiltrada) {
                    textoRetorno += "\nID: " + servico.getIdServico() +
                            " Cliente: " + servico.getCliente().getNome() +
                            " Equipamento: " + servico.getIdEquipamento() +
                            " Data Entrega: " + sdf.format(servico.getDataEntrega());
                }
                JOptionPane.showMessageDialog(null, textoRetorno);
            }
        }
    }
}