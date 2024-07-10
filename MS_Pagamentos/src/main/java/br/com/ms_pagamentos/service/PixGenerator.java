package br.com.ms_pagamentos.service;

import br.com.ms_pagamentos.model.Pix;

public class PixGenerator{

    public static String generatePixCode(Pix pix) {
        StringBuilder sb = new StringBuilder();

        // Payload Format Indicator
        sb.append("000201");

        // Merchant Account Information
        String merchantAccountInfo = "0014BR.GOV.BCB.PIX" +
                "01" + String.format("%02d", pix.getChavePix().length()) + pix.getChavePix();
        sb.append("26" + String.format("%02d", merchantAccountInfo.length()) + merchantAccountInfo);

        // Merchant Category Code (default "0000" for undefined)
        sb.append("52040000");

        // Transaction Currency (986 for BRL)
        sb.append("5303986");

        // Transaction Amount
        if (pix.getValor() > 0) {
            sb.append("54" + String.format("%02d", String.format("%.2f", pix.getValor()).length()) + String.format("%.2f", pix.getValor()));
        }

        // Country Code
        sb.append("5802BR");

        // Merchant Name
        sb.append("59" + String.format("%02d", pix.getNomeRecebedor().length()) + pix.getNomeRecebedor());

        // Merchant City
        sb.append("60" + String.format("%02d", pix.getCidadeRecebedor().length()) + pix.getCidadeRecebedor());

        // Additional Data Field Template
        String additionalData = "0503***";
        if (pix.getIdentificadorTransacao() != null && !pix.getIdentificadorTransacao().isEmpty()) {
            additionalData = "05" + String.format("%02d", pix.getIdentificadorTransacao().length()) + pix.getIdentificadorTransacao();
        }
        sb.append("62" + String.format("%02d", additionalData.length()) + additionalData);

        // CRC (Checksum)
        sb.append("6304");
        sb.append(calculateCRC(sb.toString()));

        return sb.toString();
    }

    private static String calculateCRC(String payload) {
        int polynomial = 0x1021;
        int result = 0xFFFF;

        byte[] bytes = payload.getBytes();
        for (byte b : bytes) {
            result ^= (b << 8);
            for (int bit = 0; bit < 8; bit++) {
                if ((result & 0x8000) != 0) {
                    result = (result << 1) ^ polynomial;
                } else {
                    result = result << 1;
                }
            }
        }
        result &= 0xFFFF;
        return String.format("%04X", result).toUpperCase();
    }

}
