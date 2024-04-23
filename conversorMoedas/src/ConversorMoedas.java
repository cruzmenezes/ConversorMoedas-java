import java.util.Scanner;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


public class ConversorMoedas {

    public static double obterTaxaDeCambio(String base, String alvo) throws IOException {
        String chaveAPI = "8b93693f4cc5c35f31dd5ddc";
        URL url = new URL("https://api.exchangerate-api.com/v4/latest/" + base);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");
        BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
        StringBuilder resposta = new StringBuilder();
        String linha;
        while ((linha = leitor.readLine()) != null) {
            resposta.append(linha);
        }
        leitor.close();
        JSONObject dados = new JSONObject(resposta.toString());
        JSONObject taxas = dados.getJSONObject("rates");
        return taxas.getDouble(alvo);
    }

    public static void converterMoeda(String base, String alvo) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite a quantidade de " + base + " que deseja converter para " + alvo + ":");
        double quantidade = scanner.nextDouble();
        double taxa = obterTaxaDeCambio(base, alvo);
        double resultado = quantidade * taxa;
        System.out.println(quantidade + " " + base + " equivale a " + resultado + " " + alvo);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bem-vindo ao Conversor de Moedas!");

        while (true) {
            System.out.println("\nOpções de Conversão:");
            System.out.println("1. USD para EUR");
            System.out.println("2. EUR para USD");
            System.out.println("3. GBP para USD");
            System.out.println("4. USD para GBP");
            System.out.println("5. EUR para GBP");
            System.out.println("6. GBP para EUR");
            System.out.println("7. BRL para USD");
            System.out.println("8. USD para BRL");
            System.out.println("0. Sair");
            System.out.println("Escolha uma opção:");

            int opcao = scanner.nextInt();

            try {
                switch (opcao) {
                    case 1:
                        converterMoeda("USD", "EUR");
                        break;
                    case 2:
                        converterMoeda("EUR", "USD");
                        break;
                    case 3:
                        converterMoeda("GBP", "USD");
                        break;
                    case 4:
                        converterMoeda("USD", "GBP");
                        break;
                    case 5:
                        converterMoeda("EUR", "GBP");
                        break;
                    case 6:
                        converterMoeda("GBP", "EUR");
                        break;
                    case 7:
                        converterMoeda("BRL", "EUR");
                        break;
                    case 8:
                        converterMoeda("EUR", "BRL");
                        break;
                    case 0:
                        System.out.println("Obrigado por usar o Conversor de Moedas!");
                        System.exit(0);
                    default:
                        System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                }
            } catch (IOException e) {
                System.out.println("Erro ao obter taxa de câmbio. Verifique sua conexão com a internet ou tente novamente mais tarde.");
            }
        }
    }
}
