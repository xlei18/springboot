package com.leo.demo.web3j.service;

import org.springframework.util.StringUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;
import rx.Subscription;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by leo on 2018/2/13.
 */
public class Web3jService {

    private String url = "http://10.0.20.47:8545";


    public void demo() throws Exception {
        Web3j web3j = Web3j.build(new HttpService(url));
        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
        String ver = web3ClientVersion.getWeb3ClientVersion();
        System.out.println("============= ver = " + ver + " =============");


        Admin admin = Admin.build(new HttpService(url));
        String fromAccountId = "0xcf02576d985df0863105d240ab8d433c4142381b";
        String toAccountId = "0x93b36463e9ed47da8c0679a0e51aeb2de2b71bcd";
        String password = "123456";
        BigInteger ACCOUNT_UNLOCK_DURATION = new BigInteger("30000");
        PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(fromAccountId, password).send();
        if (personalUnlockAccount.accountUnlocked()) {
            personalUnlockAccount = admin.personalUnlockAccount(fromAccountId, password, ACCOUNT_UNLOCK_DURATION).sendAsync().get(5, TimeUnit.MINUTES);
        }

        String inputValue = "ssssss";
        String hesString = Numeric.toHexString(inputValue.getBytes());
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                fromAccountId, DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        Transaction transaction = Transaction.createFunctionCallTransaction(fromAccountId, nonce, null, null, toAccountId, new BigInteger("50"), hesString);


        EthEstimateGas ethEstimateGas = admin.ethEstimateGas(transaction).sendAsync().get(5, TimeUnit.MINUTES);
        EthGetBalance ethGetBalance = admin.ethGetBalance(fromAccountId, DefaultBlockParameterName.LATEST).sendAsync().get(5, TimeUnit.MINUTES);
        System.out.println("============= ethGetBalance = " + ethGetBalance.getBalance().toString() + " =============");


        EthSendTransaction ethSendTransaction = admin.ethSendTransaction(transaction).sendAsync().get(5, TimeUnit.MINUTES);
        String transHash = ethSendTransaction.getTransactionHash();
        System.out.println("============= transHash = " + transHash + " =============");

        System.out.println("============= getResult = " + admin.ethGetTransactionByHash(transHash).send().getResult() + " =============");
    }

    public void filter() throws Exception {
        Web3j web3j = Web3j.build(new HttpService(url));
        final HashMap<String, String> map = new HashMap<>();
        while (true) {
            Subscription subscription = web3j.transactionObservable().subscribe(tx -> {
                if (tx.getTo().equals("0x93b36463e9ed47da8c0679a0e51aeb2de2b71bcd")) {
                    map.put(tx.getHash(),"From=" + tx.getFrom() + ",value=" + tx.getValue());
                    System.out.println("============= from = " + tx.getFrom() + " =============");
                    System.out.println("============= value = " + tx.getValue() + " =============");
                    System.out.println("============= hash = " + tx.getHash() + " =============");
                }
            });
            System.out.println(map.size());
            map.keySet().stream().forEach(k->{
                System.out.println(k + "=" + map.get(k));
            });
            map.clear();
//            subscription.unsubscribe();
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%5");
            Thread.sleep(10000);
        }

    }
}
