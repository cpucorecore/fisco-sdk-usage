package com.ancun.blockchain;

import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.precompiled.consensus.ConsensusPrecompiled;
import org.fisco.bcos.sdk.model.TransactionReceipt;

import java.util.List;

public class App {
    private BcosSDK sdk;
    private Client client;
    private ConsensusPrecompiled consensusPrecompiled;

    // the format of the contactAddress must be an Address type, length must be 42
    private static final String contractAddress = "0x0000000000000000000000000000000000001003";

    private void prepareSDK(String configFilePath, Integer groupId) {
        if (null == sdk) {
            sdk = BcosSDK.build(configFilePath);
        }

        if (null == client) {
            client = sdk.getClient(groupId);
            consensusPrecompiled = ConsensusPrecompiled.load(contractAddress, client, client.getCryptoSuite().getCryptoKeyPair());
        }
    }
    //添加共识节点
    private void addSealer(String nodeId) {
        TransactionReceipt receipt = consensusPrecompiled.addSealer(nodeId);
        System.out.println(receipt.toString());
    }
    //移除节点，变为游离节点
    private void removeNode(String nodeId) {
        TransactionReceipt receipt = consensusPrecompiled.remove(nodeId);
        System.out.println(receipt.toString());
    }
    //添加观察者节点
    private void addObserver(String nodeId) {
        TransactionReceipt receipt = consensusPrecompiled.addObserver(nodeId);
        System.out.println(receipt.toString());
    }
    //获取共识节点列表
    private List<String> getSealers() {
        return client.getSealerList().getSealerList();
    }
    //获取观察者节点列表
    private List<String> getObservers() {
        return client.getObserverList().getObserverList();
    }

    public static void main(String[] args) {
        String configFilePath = "D:\\ideaProject\\fisco-sdk-usage-master\\src\\main\\resources\\config.toml";
        Integer groupId = 1;
        //替换为需要管理的节点id
        String nodeId = "314619606fdb2fc63dcde2ef231b2dde3abe552ad0a820cdeda34c825114a5e5e7ddebed75cff4f0b404e1a6a36922cdf7186cd8092e665af0194173d4fbb229";

        App app = new App();
        app.prepareSDK(configFilePath, groupId);

        System.out.println("original sealerList is:" + app.getSealers());
        app.addSealer(nodeId);
        System.out.println("After adding the node, the sealerList is:" + app.getSealers());
        app.removeNode(nodeId);
        System.out.println("After removing the node, the sealerList is:" + app.getSealers());

        System.out.println("original observerList is:" + app.getObservers());
        app.addObserver(nodeId);
        System.out.println("After adding the node, the observerList is:" + app.getObservers());
    }
}
