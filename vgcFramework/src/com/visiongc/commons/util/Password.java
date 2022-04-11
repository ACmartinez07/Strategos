package com.visiongc.commons.util;

import com.visiongc.commons.util.lang.ChainedRuntimeException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;
import java.util.Random;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.script.*;

public class Password
{

    public Password()
    {
    }

    public static String encriptaCadena(String mensaje)
    {
        StringBuilder mensaje1 = new StringBuilder();
        try
        {
            byte rpta[] = encripta(mensaje);
            for(int i = 0; i < rpta.length; i++)
            {
                String cad = Integer.toHexString(rpta[i]).toUpperCase();
                if(i != 0)
                    mensaje1.append("-");
                if(cad.length() < 2)
                {
                    mensaje1.append("0");
                    mensaje1.append(cad);
                } else
                if(cad.length() > 2)
                    mensaje1.append(cad.substring(6, 8));
                else
                    mensaje1.append(cad);
            }

        }
        catch(InvalidKeyException e)
        {
            e.printStackTrace();
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch(NoSuchPaddingException e)
        {
            e.printStackTrace();
        }
        catch(InvalidAlgorithmParameterException e)
        {
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch(IllegalBlockSizeException e)
        {
            e.printStackTrace();
        }
        catch(BadPaddingException e)
        {
            e.printStackTrace();
        }
        return mensaje1.toString();
    }

    public static String desencripta(String cadena)
    {
        String mensaje = "";
        try
        {
            String sBytes[] = cadena.split("-");
            byte bytes[] = new byte[sBytes.length];
            for(int i = 0; i < sBytes.length; i++)
                bytes[i] = (byte)Integer.parseInt(sBytes[i], 16);

            mensaje = desencripta(bytes);
        }
        catch(InvalidKeyException e)
        {
            e.printStackTrace();
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch(NoSuchPaddingException e)
        {
            e.printStackTrace();
        }
        catch(InvalidAlgorithmParameterException e)
        {
            e.printStackTrace();
        }
        catch(IllegalBlockSizeException e)
        {
            e.printStackTrace();
        }
        catch(BadPaddingException e)
        {
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return mensaje;
    }

    private static byte[] encripta(String message)
        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException
    {
        MessageDigest md = MessageDigest.getInstance("md5");
        byte digestOfPassword[] = md.digest(clave.getBytes("utf-8"));
        byte keyBytes[] = Arrays.copyOf(digestOfPassword, 24);
        int j = 0;
        int k = 16;
        while(j < 8) 
            keyBytes[k++] = keyBytes[j++];
        javax.crypto.SecretKey key = new SecretKeySpec(keyBytes, "DESede");
        IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(1, key, iv);
        byte plainTextBytes[] = message.getBytes("utf-8");
        byte cipherText[] = cipher.doFinal(plainTextBytes);
        return cipherText;
    }

    private static String desencripta(byte message[])
        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
    {
        MessageDigest md = MessageDigest.getInstance("md5");
        byte digestOfPassword[] = md.digest(clave.getBytes("utf-8"));
        byte keyBytes[] = Arrays.copyOf(digestOfPassword, 24);
        int j = 0;
        int k = 16;
        while(j < 8) 
            keyBytes[k++] = keyBytes[j++];
        javax.crypto.SecretKey key = new SecretKeySpec(keyBytes, "DESede");
        IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        decipher.init(2, key, iv);
        byte plainText[] = decipher.doFinal(message);
        return new String(plainText, "UTF-8");
    }

    public static String encript(String inS)
    {
        String newString = "";
        int largoTexto = inS.length();
        try
        {
            for(int counter = 1; counter <= largoTexto; counter++)
            {
                int mask;
                if(counter < 10)
                {
                    if(counter % 2 == 0)
                        mask = (counter * counter) / 12;
                    else
                        mask = (int)Math.sqrt(Math.pow(counter, 3D));
                } else
                if(counter < 20)
                    mask = (int)(Math.round(Math.pow(counter, 3D)) / 523L);
                else
                    mask = (int)(Math.round(Math.sqrt(Math.pow(counter, 5D))) / (long)(140 + counter));
                if(mask % 2 == 1)
                    mask++;
                else
                    mask *= 2;
                if(mask > 255)
                    mask = Math.round(mask) / 255;
                String tmpStr = inS.substring(counter - 1, counter);
                char arreglo_char[] = tmpStr.toCharArray();
                char tmpChar = (char)(arreglo_char[0] ^ mask);
                if(tmpChar == 0)
                    tmpChar = arreglo_char[0];
                newString = (new StringBuilder(String.valueOf(newString))).append(tmpChar).toString();
            }

            return newString;
        }
        catch(Throwable e)
        {
            throw new ChainedRuntimeException(e.getMessage(), e);
        }
    }

    public static String decriptPassWord(String inPwd)
    {
        String pl = "";
        String pr = "";
        int l = 0;
        int splitMode = 0;
        String newString = "";
        if(inPwd == null)
            return null;
        try
        {
            if(inPwd.substring(0, 2).equalsIgnoreCase("&H"))
            {
                String newPwd = "";
                int tamano = inPwd.length();
                for(int i = 2; i < tamano; i += 2)
                    newPwd = (new StringBuilder(String.valueOf(newPwd))).append(String.valueOf((char)Integer.parseInt(inPwd.substring(i, i + 2), 16))).toString();

                inPwd = newPwd;
            }
            inPwd = encript(inPwd);
            char Arreglo_Char[] = inPwd.toCharArray();
            l = Arreglo_Char[0];
            splitMode = Arreglo_Char[1];
            inPwd = inPwd.substring(2);
            if(l % 2 == 0)
            {
                if(splitMode == 1)
                {
                    pl = inPwd.substring(0, l / 2);
                    pr = inPwd.substring(inPwd.length() - l / 2);
                } else
                if(splitMode == 2)
                {
                    pr = inPwd.substring(0, l / 2);
                    pl = inPwd.substring(inPwd.length() - l / 2);
                } else
                if(splitMode == 3)
                {
                    pl = inPwd.substring(0, l);
                    pr = "";
                } else
                if(splitMode == 4)
                {
                    pr = inPwd.substring(0, l / 2);
                    pl = inPwd.substring(l / 2, l);
                } else
                if(splitMode == 5)
                {
                    pl = inPwd.substring(inPwd.length() - l);
                    pr = "";
                } else
                {
                    pl = inPwd.substring(inPwd.length() - l);
                    pr = pl.substring(0, l / 2);
                    pl = pl.substring(l / 2);
                }
            } else
            if(splitMode == 1)
            {
                pl = inPwd.substring(0, l / 2 + 1);
                pr = inPwd.substring(inPwd.length() - l / 2);
            } else
            if(splitMode == 2)
            {
                pr = inPwd.substring(0, l / 2);
                pl = inPwd.substring(inPwd.length() - l / 2 - 1);
            } else
            if(splitMode == 3)
            {
                pl = inPwd.substring(0, l);
                pr = "";
            } else
            if(splitMode == 4)
            {
                pr = inPwd.substring(0, l / 2);
                pl = inPwd.substring(l / 2, l);
            } else
            if(splitMode == 5)
            {
                pl = inPwd.substring(inPwd.length() - l);
                pr = "";
            } else
            {
                pl = inPwd.substring(inPwd.length() - l);
                pr = pl.substring(0, l / 2);
                pl = pl.substring(l / 2);
            }
            newString = (new StringBuilder(String.valueOf(pl))).append(pr).toString();
            return newString;
        }
        catch(Throwable e)
        {
            throw new ChainedRuntimeException(e.getMessage(), e);
        }
    }

    public static String encriptPassWord(String inPwd)
    {
        Random gen = new Random();
        int SplitMode = gen.nextInt(6) + 1;
        int l = inPwd.length();
        try
        {
            String pl;
            String pr;
            if(l % 2 == 0)
            {
                pl = inPwd.substring(0, l / 2);
                pr = inPwd.substring(l / 2);
            } else
            {
                pl = inPwd.substring(0, l / 2 + 1);
                pr = inPwd.substring(l / 2 + 1);
            }
            String dummy = "";
            for(int c = 1; c <= 28 - l; c++)
                dummy = (new StringBuilder(String.valueOf(dummy))).append((char)(gen.nextInt(200) + 1)).toString();

            Character cl = new Character((char)l);
            String newString;
            if(SplitMode == 1)
                newString = encript((new StringBuilder(String.valueOf(cl.toString()))).append((char)SplitMode).append(pl).append(dummy).append(pr).toString());
            else
            if(SplitMode == 2)
                newString = encript((new StringBuilder(String.valueOf(cl.toString()))).append((char)SplitMode).append(pr).append(dummy).append(pl).toString());
            else
            if(SplitMode == 3)
                newString = encript((new StringBuilder(String.valueOf(cl.toString()))).append((char)SplitMode).append(pl).append(pr).append(dummy).toString());
            else
            if(SplitMode == 4)
                newString = encript((new StringBuilder(String.valueOf(cl.toString()))).append((char)SplitMode).append(pr).append(pl).append(dummy).toString());
            else
            if(SplitMode == 5)
                newString = encript((new StringBuilder(String.valueOf(cl.toString()))).append((char)SplitMode).append(dummy).append(pl).append(pr).toString());
            else
                newString = encript((new StringBuilder(String.valueOf(cl.toString()))).append((char)SplitMode).append(dummy).append(pr).append(pl).toString());
            String newPwd = "&H";
            String token = null;
            for(int i = 0; i < newString.length(); i++)
            {
                token = Integer.toHexString(newString.charAt(i));
                if(token.length() == 0)
                    newPwd = (new StringBuilder(String.valueOf(newPwd))).append("00").toString();
                else
                if(token.length() == 1)
                    newPwd = (new StringBuilder(String.valueOf(newPwd))).append("0").append(token).toString();
                else
                    newPwd = (new StringBuilder(String.valueOf(newPwd))).append(token).toString();
            }

            return newPwd;
        }
        catch(Throwable e)
        {
            throw new ChainedRuntimeException(e.getMessage(), e);
        }
    }

    public static String getStringMessageDigest(String message, String algorithm)
    {
        byte digest[] = (byte[])null;
        byte buffer[] = message.getBytes();
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.reset();
            messageDigest.update(buffer);
            digest = messageDigest.digest();
        }
        catch(NoSuchAlgorithmException ex)
        {
            System.out.println("Error creando Digest");
        }
        return toHexadecimal(digest);
    }

    private static String toHexadecimal(byte digest[])
    {
        String hash = "";
        byte abyte0[];
        int j = (abyte0 = digest).length;
        for(int i = 0; i < j; i++)
        {
            byte aux = abyte0[i];
            int b = aux & 0xff;
            if(Integer.toHexString(b).length() == 1)
                hash = (new StringBuilder(String.valueOf(hash))).append("0").toString();
            hash = (new StringBuilder(String.valueOf(hash))).append(Integer.toHexString(b)).toString();
        }

        return hash;
    }

    public static String decriptJavaScript(String texto)
    {
        String returnValue = "";
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        String script = "var _keyStr = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/='; \tfunction decode(input)\t{\t\tvar output = '';\t\tvar chr1, chr2, chr3;\t\tvar enc1, enc2, enc3, enc4;\t\tvar i = 0; \t\t\t\tinput = input.replace(/[^A-Za-z0-9\\+\\/\\=]/g, '');\t\t\t\twhile (i < input.length)\t\t{\t\t\tenc1 = this._keyStr.indexOf(input.charAt(i++));\t\t\tenc2 = this._keyStr.indexOf(input.charAt(i++));\t\t\tenc3 = this._keyStr.indexOf(input.charAt(i++));\t\t\tenc4 = this._keyStr.indexOf(input.charAt(i++));\t\t\t\t\t\tchr1 = (enc1 << 2) | (enc2 >> 4);\t\t\tchr2 = ((enc2 & 15) << 4) | (enc3 >> 2);\t\t\tchr3 = ((enc3 & 3) << 6) | enc4;\t\t\t\t\t\toutput = output + String.fromCharCode(chr1);\t\t\t\t\t\tif (enc3 != 64)\t\t\t\toutput = output + String.fromCharCode(chr2);\t\t\tif (enc4 != 64)\t\t\t\toutput = output + String.fromCharCode(chr3);\t\t}\t\t\t\toutput = _utf8_decode(output);\t\t\t\treturn output;\t} \t\tfunction _utf8_decode(utftext)\t{\t\tvar string = '';\t\tvar i = 0;\t\tvar c = c1 = c2 = 0;\t\t\t\twhile ( i < utftext.length )\t\t{\t\t\tc = utftext.charCodeAt(i);\t\t\t\t\t\tif (c < 128) \t\t\t{\t\t\t\tstring += String.fromCharCode(c);\t\t\t\ti++;\t\t\t}\t\t\telse if((c > 191) && (c < 224))\t\t\t{\t\t\t\tc2 = utftext.charCodeAt(i+1);\t\t\t\tstring += String.fromCharCode(((c & 31) << 6) | (c2 & 63));\t\t\t\ti += 2;\t\t\t}\t\t\telse\t\t\t{\t\t\t\tc2 = utftext.charCodeAt(i+1);\t\t\t\tc3 = utftext.charCodeAt(i+2);\t\t\t\tstring += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));\t\t\t\ti += 3;\t\t\t}\t\t}\t\t\t\treturn string;\t}";
        try
        {
            engine.eval(script);
            Invocable inv = (Invocable)engine;
            returnValue = (String)inv.invokeFunction("decode", new Object[] {
                texto
            });
        }
        catch(ScriptException e)
        {
            throw new ChainedRuntimeException(e.getMessage(), e);
        }
        catch(NoSuchMethodException e)
        {
            throw new ChainedRuntimeException(e.getMessage(), e);
        }
        return returnValue;
    }

    public static String MD2 = "MD2";
    public static String MD5 = "MD5";
    public static String SHA1 = "SHA-1";
    public static String SHA256 = "SHA-256";
    public static String SHA384 = "SHA-384";
    public static String SHA512 = "SHA-512";
    private static String clave = "LAS AVES VUELAN LIBREMENTE";

}