package com.company;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {

    public static void main(String[] args) {

        String decrypt1 = "k12ww 7s49v ,?286?mv 27rq s33od4 6fh77k r4og2 hu+";
        String decrypt2 = "yd23 un394 hti58 j934+0 67htr9 8?173h t23r96 rj8s3 y73x";

        // taking all spaces out of both strings
        decrypt1 = decrypt1.replace(" ", "");
        decrypt2 = decrypt2.replace(" ", "");

        // taking all numbers out of both strings
        decrypt1 = decrypt1.replaceAll("[0-9]+", "");
        decrypt2 = decrypt2.replaceAll("[0-9]+", "");

        // replaceing all , with :
        decrypt1 = decrypt1.replace(",", ":");
        decrypt2 = decrypt2.replace(",", ":");

        // replacing all + with .
        decrypt1 = decrypt1.replace("+", ".");
        decrypt2 = decrypt2.replace("+", ".");

        // replacing all ? with /
        decrypt1 = decrypt1.replace("?", "/");
        decrypt2 = decrypt2.replace("?", "/");


        String toDecrypt = decrypt1;
        int threeShift = 3;

        String joinDecrypted1 = "";

        int lengthOfString = toDecrypt.length();

        for(int x = 0; x < lengthOfString; x++){

            char c = (char)(toDecrypt.charAt(x));

            if (!Character.isAlphabetic(c)){
                joinDecrypted1 += (char)(toDecrypt.charAt(x));
            }else{

                c = (char) (toDecrypt.charAt(x) - threeShift);

                if (c < 'a'){
                    joinDecrypted1 += (char)(toDecrypt.charAt(x) + (26-threeShift));
                }
                else{
                    joinDecrypted1 += (char)(toDecrypt.charAt(x) - threeShift);
                }
            }

        }

        String toDecrypt2 = decrypt2;
        int fiveShift = 5;

        String joinDecrypted2 = "";

        int lengthOfString2 = toDecrypt2.length();

        for(int x = 0; x < lengthOfString2; x++){

            char c = (char)(toDecrypt2.charAt(x));
            if (!Character.isAlphabetic(c)){
                joinDecrypted2 += (char)(toDecrypt2.charAt(x));
            }else{

                c = (char) (toDecrypt2.charAt(x) - fiveShift);

                if (c < 'a'){
                    joinDecrypted2 += (char)(toDecrypt2.charAt(x) + (26-fiveShift));
                }
                else{
                    joinDecrypted2 += (char)(toDecrypt2.charAt(x) - fiveShift);
                }
            }
        }


        String myURL = joinDecrypted1 + joinDecrypted2;

        System.out.println(myURL);



        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(myURL)).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(Main::parseThis)
                .join();

    }

    public static String parseThis(String responseBody){
        JSONArray comments = new JSONArray(responseBody);
        for (int i = 0; i < comments.length(); i++){
            JSONObject comment = comments.getJSONObject(i);
            String email = comment.getString("email");
            System.out.println(email);
        }

        return null;
    }




}
