import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Garantia {
    private static int idAutomatico = 1;
    private int idGarantia;
    private Date dataInicial;
    private Date dataFinal;
    private Servico servico;

    public Garantia(Date dataInicial, Date dataFinal, Servico servico) {
        this.idGarantia = idAutomatico++;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.servico = servico;
    }

    public int getIdGarantia() {
        return idGarantia;
    }

    public void setIdGarantia(int id) {
        this.idGarantia = id;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
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

    public static void cadastrarGarantia(ArrayList<Garantia> listaGarantias, ArrayList<Servico> listaServicos) {

        if (listaServicos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não é possível cadastrar garantias. Nenhuma serviço cadastrado.");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            // Cadastro da data inicial
            Date dataInicial = getDateInput("Data Inicial (dd/MM/yyyy):", sdf);
            if (dataInicial == null) {
                JOptionPane.showMessageDialog(null, "O campo de data inicial não pode estar vazio. Tente novamente.");
                return;
            }
            // Cadastro da data final
            Date dataFinal = getDateInput("Data Final (dd/MM/yyyy):", sdf);
            if (dataFinal == null) {
                JOptionPane.showMessageDialog(null, "O campo de data final não pode estar vazio. Tente novamente.");
                return;
            }

            //Escolha de Serviço:
            ArrayList<Servico> optionsServicos = new ArrayList<Servico>();
            for (Servico servicoItem : listaServicos) {
                optionsServicos.add(new Servico(servicoItem.getIdServico(), servicoItem.getIdEquipamento(), servicoItem.getCliente()));
            }
            Object[] options = optionsServicos.toArray();
            Servico servicoEscolhido = (Servico) JOptionPane.showInputDialog(null, "Escolha um serviço", "Escolha um serviço", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            Garantia garantiaCadastrar = new Garantia(dataInicial, dataFinal, listaServicos.stream().filter(servico -> servicoEscolhido.getIdServico() == servico.getIdServico()).findFirst().orElse(null));

            // Adicione a nova garantia a lista.
            listaGarantias.add(garantiaCadastrar);
            JOptionPane.showMessageDialog(null, "Garantia cadastrada com sucesso!");
        }
    }

    public static void editarGarantia(ArrayList<Garantia> listaGarantias, ArrayList<Servico> listaServicos) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        boolean encontrado = false;
        Garantia garantiaEditar = null;


        if (listaGarantias.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma garantia cadastrada.");
        } else {
            listarGarantia(listaGarantias);

            String idEditarStr = JOptionPane.showInputDialog("Qual o ID da garantia que deseja editar?");
            if (idEditarStr == null || idEditarStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O ID da garantia não pode estar vazio. Tente novamente.");
            }
            try {
                int idEditar = Integer.parseInt(idEditarStr);

                for (Garantia garantia : listaGarantias) {
                    if (garantia.getIdGarantia() == idEditar) {
                        garantiaEditar = garantia;
                        encontrado = true;
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, insira um número válido para o ID da Garantia.");
                return;
            }

            if (encontrado) {
                boolean edicaoConcluida = false;

                while (!edicaoConcluida) {
                    int opcaoEditar;

                    String[] opcoesEditar = {"Trocar Serviço", "Editar a data inicial",
                            "Editar a data final", "Voltar"};
                    opcaoEditar = JOptionPane.showOptionDialog(
                            null,
                            "Escolha uma opção para edição:",
                            "Editar serviço",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            opcoesEditar,
                            opcoesEditar[0]
                    );

                    switch (opcaoEditar) {

                        case 0:    //Trocar Serviço
                            if (listaServicos.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Não é possível editar a garantia. Nenhuma serviço cadastrado.");
                            } else {
                                Object[] options = listaServicos.toArray();
                                Servico novoServico = (Servico) JOptionPane.showInputDialog(null, "Escolha um serviço", "Escolha um serviço", JOptionPane.PLAIN_MESSAGE, null, options, garantiaEditar.getServico());
                                garantiaEditar.setServico(novoServico);
                            }
                            break;

                        case 1: //Edição da data inicial
                            Date novaDataInicial = getDateInput(
                                    "nova data inicial (dd/MM/yyyy): ", sdf);
                            if (novaDataInicial != null) {
                                garantiaEditar.setDataInicial(novaDataInicial);
                            }
                            break;

                        case 2: //Edição da data final
                            Date novaDataFinal = getDateInput(
                                    "Nova data final (dd/MM/yyyy):", sdf);
                            if (novaDataFinal != null) {
                                garantiaEditar.setDataFinal(novaDataFinal);
                            }
                            break;

                        case 3:
                            JOptionPane.showMessageDialog(null, "Edição concluída.");
                            edicaoConcluida = true;
                            break;
                    }

                    JOptionPane.showMessageDialog(null, "Garantia editada!");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Garantia não existe.");
            }
        }
    }

    public static void listarGarantia(ArrayList<Garantia> listaGarantias) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        if (listaGarantias.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma garantia cadastrada.");
        } else {
            for (Garantia garantia : listaGarantias) {
                JOptionPane.showMessageDialog(null, "ID: " + garantia.getIdGarantia() +
                        "\nServiço: " + garantia.getServico().toString() +
                        "\nData Inicial: " + sdf.format(garantia.getDataInicial()) +
                        "\nData Final: " + sdf.format(garantia.getDataFinal()));
            }
        }
    }

    public static boolean excluirGarantia(int idGarantia, ArrayList<Garantia> listaGarantias) {
        for (Garantia garantia : listaGarantias) {
            if (garantia.getIdGarantia() == idGarantia) {
                listaGarantias.remove(garantia);
                return true; // Retorna true se a garantia foi excluída
            }
        }
        return false; // Retorna false se a garantia não foi encontrada
    }
}