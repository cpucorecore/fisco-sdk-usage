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

    private void addSealer(String nodeId) {
        TransactionReceipt receipt = consensusPrecompiled.addSealer(nodeId);
        System.out.println(receipt.toString());
    }

    private List<String> getSealers() {
        return client.getSealerList().getSealerList();
    }

    public static void main(String[] args) {
        String configFilePath = "/Users/sky/IdeaProjects/github.com/cpucorecore/node-mgr/src/main/resources/config.toml";
        Integer groupId = 1;
        String nodeId = "c031860dc31147f63ccb10e9d335f19595e7f0c79af482f347f99337950d3afde307769b0040ff3789084e645556c8b73893ed1d17672bc9ad65c520a169044e";

        App app = new App();
        app.prepareSDK(configFilePath, groupId);

        System.out.println(app.getSealers());
        app.addSealer(nodeId);
        System.out.println(app.getSealers());
    }
}
