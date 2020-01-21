package com.piv.money.transfers;

import com.piv.money.transfers.dto.AccountDto;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Created by Ivan on 21.01.2020.
 */
public class UtilsTest {
    public static final String TEST_ACCOUNT_ID = "test_id";
    public static final String TEST_ACCOUNT_ID_1 = "test_id_1";
    public static final String TEST_ACCOUNT_ID_2 = "test_id_2";
    public static final String ABSENT_ACCOUNT_ID = "absent_id";
    public static final String NOT_NUMBER = "not_number";
    public static final BigDecimal NEGATIVE_NUMBER = new BigDecimal(-1);

    private static ObjectMapper mapper = new ObjectMapper();

    public static HttpResponse findAccountResponse(String id) throws IOException {
        String url = String.format("http://localhost:8080/account/%s", id);
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        return client.execute(request);
    }

    public static AccountDto findAccount(String id) throws IOException {
        HttpResponse response = findAccountResponse(id);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        return mapper.readValue(EntityUtils.toString(response.getEntity()), AccountDto.class);
    }

    public static HttpResponse createAccountResponse(String initialValue) throws IOException {
        String url = "http://localhost:8080/account/create";
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);
        if (initialValue != null) {
            request.setEntity(new StringEntity(initialValue));
        }
        return client.execute(request);
    }

    public static AccountDto createAccount(String initialValue) throws IOException {
        HttpResponse response = createAccountResponse(initialValue);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        return mapper.readValue(EntityUtils.toString(response.getEntity()), AccountDto.class);
    }

    public static HttpResponse depositAccountResponse(String accountId, String amount) throws IOException {
        String url = "http://localhost:8080/account/deposit/" + accountId;
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);
        if (amount != null) {
            request.setEntity(new StringEntity(amount));
        }
        return client.execute(request);
    }

    public static AccountDto depositAccount(String accountId, String amount) throws IOException {
        HttpResponse response = depositAccountResponse(accountId, amount);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        return mapper.readValue(EntityUtils.toString(response.getEntity()), AccountDto.class);
    }

    public static HttpResponse withdrawAccountResponse(String accountId, String amount) throws IOException {
        String url = "http://localhost:8080/account/withdraw/" + accountId;
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);
        if (amount != null) {
            request.setEntity(new StringEntity(amount));
        }
        return client.execute(request);
    }

    public static AccountDto withdrawAccount(String accountId, String amount) throws IOException {
        HttpResponse response = withdrawAccountResponse(accountId, amount);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        return mapper.readValue(EntityUtils.toString(response.getEntity()), AccountDto.class);
    }

    public static HttpResponse deleteAccountResponse(String accountId) throws IOException {
        String url = "http://localhost:8080/account/delete/" + accountId;
        HttpClient client = HttpClientBuilder.create().build();
        HttpDelete request = new HttpDelete(url);
        return client.execute(request);
    }

    public static void deleteAccount(String accountId) throws IOException {
        HttpResponse response = deleteAccountResponse(accountId);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(Response.Status.OK.getStatusCode());
    }

    public static HttpResponse transferAmountResponse(String fromId, String toId, String amount) throws IOException {
        String url = String.format("http://localhost:8080/transfer/from/%s/to/%s", fromId, toId);
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);
        if (amount != null) {
            request.setEntity(new StringEntity(amount));
        }
        return client.execute(request);
    }

    public static void transferAmount(String fromId, String toId, String amount) throws IOException {
        HttpResponse response = transferAmountResponse(fromId, toId, amount);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(EntityUtils.toString(response.getEntity())).isEqualTo("ok");
    }
}
