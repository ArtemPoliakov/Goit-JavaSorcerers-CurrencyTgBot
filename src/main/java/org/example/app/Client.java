package org.example.app;

import org.example.app.bank.Bank;
import org.example.app.bank.Bank.BankName;
import org.example.app.bank.Currency;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.app.dto.MonoBankCurrencyDTO;
import org.example.app.dto.NbuCurrencyDTO;
import org.example.app.dto.PrivatBankCurrencyDTO;
import static org.example.app.bank.Currency.CurrencyName;
import static org.example.app.bank.Currency.CurrencyName.*;
import static java.util.Map.entry;

import lombok.Getter;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Client {
    private static final Client client = new Client();
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Getter
    private final Map<BankName, Bank> banks = Map.ofEntries(
            entry(BankName.MONO, new Bank()),
            entry(BankName.PRIVAT, new Bank()),
            entry(BankName.NBU, new Bank())
    );

    public static Client getInstance() {
        return client;
    }

    @SneakyThrows
    private Client() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(this::updateAllBanks, 0, 5, TimeUnit.MINUTES);
    }
    private void updateAllBanks() {
       CompletableFuture.runAsync(()->banks.get(BankName.MONO).setCurrencyList(getMonoBankCurrentCurrencyList()));
       CompletableFuture.runAsync(()->banks.get(BankName.PRIVAT).setCurrencyList(getPrivatBankCurrentCurrencyList()));
       CompletableFuture.runAsync(()->banks.get(BankName.NBU).setCurrencyList(getNbuCurrentCurrencyList()));
    }

    public List<Currency> getMonoBankCurrentCurrencyList() {
        List<MonoBankCurrencyDTO> monoBankCurrencyDTOList = getMonoBankCurrencyDTOList();

        Map<CurrencyName, Double> buyMap = getMonoBankCurrenciesBuy(monoBankCurrencyDTOList);
        Map<CurrencyName, Double> sellMap = getMonoBankCurrenciesSell(monoBankCurrencyDTOList);
        Currency usd = new Currency(USD, buyMap.get(USD), sellMap.get(USD));
        Currency eur = new Currency(EUR, buyMap.get(EUR), sellMap.get(EUR));

        return List.of(usd, eur);
    }

    public List<Currency> getPrivatBankCurrentCurrencyList() {
        List<PrivatBankCurrencyDTO> privatBankCurrencyDTOList = getPrivatBankCurrencyDTOList();

        Map<CurrencyName, Double> buyMap = getPrivatBankCurrenciesBuy(privatBankCurrencyDTOList);
        Map<CurrencyName, Double> sellMap = getPrivatBankCurrenciesSell(privatBankCurrencyDTOList);
        Currency usd = new Currency(USD, buyMap.get(USD), sellMap.get(USD));
        Currency eur = new Currency(EUR, buyMap.get(EUR), sellMap.get(EUR));

        return List.of(usd, eur);
    }

    public List<Currency> getNbuCurrentCurrencyList() {
        List<NbuCurrencyDTO> nbuCurrencyDTOList = getNbuCurrencyDTOList();

        Map<CurrencyName, Double> buyAndSellMap = getNbuCurrencies(nbuCurrencyDTOList);
        Currency usd = new Currency(USD, buyAndSellMap.get(USD), buyAndSellMap.get(USD));
        Currency eur = new Currency(EUR, buyAndSellMap.get(EUR), buyAndSellMap.get(EUR));

        return List.of(usd, eur);
    }


    private Map<CurrencyName, Double> getMonoBankCurrenciesBuy(List<MonoBankCurrencyDTO> monoBankCurrencyDTOList) {
        return monoBankCurrencyDTOList.stream()
                .filter(currency -> currency.getCurrencyCodeA() == 840 || currency.getCurrencyCodeA() == 978)
                .filter(currency -> currency.getCurrencyCodeB() == 980)
                .collect(Collectors.toMap(currency -> getCurrencyNameByIntegerCode(currency.getCurrencyCodeA()), MonoBankCurrencyDTO::getRateBuy));
    }

    private Map<CurrencyName, Double> getMonoBankCurrenciesSell(List<MonoBankCurrencyDTO> monoBankCurrencyDTOList) {
        return monoBankCurrencyDTOList.stream()
                .filter(currency -> currency.getCurrencyCodeA() == 840 || currency.getCurrencyCodeA() == 978)
                .filter(currency -> currency.getCurrencyCodeB() == 980)
                .collect(Collectors.toMap(currency -> getCurrencyNameByIntegerCode(currency.getCurrencyCodeA()), MonoBankCurrencyDTO::getRateSell));
    }

    private Map<CurrencyName, Double> getPrivatBankCurrenciesBuy(List<PrivatBankCurrencyDTO> privatBankCurrencyDTOList) {
        return privatBankCurrencyDTOList.stream()
                .collect(Collectors.toMap(currency-> getCurrencyNameByStringCode(currency.getCcy()), PrivatBankCurrencyDTO::getBuy));
    }

    private Map<CurrencyName, Double> getPrivatBankCurrenciesSell(List<PrivatBankCurrencyDTO> privatBankCurrencyDTOList) {
        return privatBankCurrencyDTOList.stream()
                .collect(Collectors.toMap(currency-> getCurrencyNameByStringCode(currency.getCcy()), PrivatBankCurrencyDTO::getSale));
    }

    private Map<CurrencyName, Double> getNbuCurrencies(List<NbuCurrencyDTO> nbuCurrencyDTOList) {
        return nbuCurrencyDTOList.stream()
                .filter(currency -> currency.getCurrencyCode() == 840 || currency.getCurrencyCode() == 978)
                .collect(Collectors.toMap(currency-> getCurrencyNameByStringCode(currency.getCurrencyCodeL()), NbuCurrencyDTO::getAmount));
    }


    @SneakyThrows
    private List<MonoBankCurrencyDTO> getMonoBankCurrencyDTOList() {
        var response = getStringHttpResponse(ApplicationConstants.MONO_URL);
        return objectMapper.readValue(response.body(), new TypeReference<>() {});
    }

    @SneakyThrows
    private List<PrivatBankCurrencyDTO> getPrivatBankCurrencyDTOList() {
        var response = getStringHttpResponse(ApplicationConstants.PRIVAT_URL);
        return objectMapper.readValue(response.body(), new TypeReference<>() {});
    }

    @SneakyThrows
    private List<NbuCurrencyDTO> getNbuCurrencyDTOList() {
        var response = getStringHttpResponse(ApplicationConstants.NBU_URL);
        return objectMapper.readValue(response.body(), new TypeReference<>() {});
    }


    @SneakyThrows
    private HttpResponse<String> getStringHttpResponse(String url) {
        HttpRequest getRequest = createGetRequest(url);
        return httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
    }

    @SneakyThrows
    private HttpRequest createGetRequest(String url) {
        return HttpRequest.newBuilder(new URI(url))
                .header("Content-Type", "application/json")
                .header("accept", "application/json")
                .timeout(Duration.of(10, ChronoUnit.SECONDS))
                .GET()
                .build();
    }


    private CurrencyName getCurrencyNameByStringCode(String currencyCode){
        return switch (currencyCode) {
            case "USD" -> USD;
            case "EUR" -> EUR;
            default -> UNDEFINED;
        };
    }

    private CurrencyName getCurrencyNameByIntegerCode(int currencyCode) {
        return switch (currencyCode) {
            case 840 -> USD;
            case 978 -> EUR;
            default -> UNDEFINED;
        };
    }
}
