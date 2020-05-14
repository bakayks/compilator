package kg.inai.model;

import kg.inai.entity.Reg;

import java.util.List;

public class Compilator {
    static boolean esGeht = false;
    static String wordM = "";
    static boolean isTrue = false;

    public static void setIsTrue(boolean isTrue) {
        Compilator.isTrue = isTrue;
    }

    public static boolean isIsTrue() {
        return isTrue;
    }

    static int np = 0;
    //Insert grammer here
    static String  grammer[][];

    public static void setGrammar(List<Reg> regs){
        String[][] grammar = new String[regs.size()][];
        for(int i = 0; i < regs.size(); i++){
            int count = 0;
            for(int x = 0; x < regs.get(i).getName().length(); x++){
                if(!Character.isAlphabetic(regs.get(i).getName().charAt(x))){
                    count++;
                }
            }
            grammar[i] = new String[count];
            String gram = regs.get(i).getName();
            gram = gram.replaceAll("->","|");
            String temp = "";
            int count1 = 0;
            for(int z = 0; z < gram.length(); z++){
                if(Character.isAlphabetic(gram.charAt(z))){
                    temp += gram.charAt(z);
                }else{
                    grammar[i][count1] = temp;
                    count1++;
                    temp = "";
                }
            }
            grammar[i][count1] = temp;
        }
        grammer = grammar;
    }

    //Checks if the passed string can be achieved for the grammer
    static String check(String a){
        String to_ret = "";
        int count = 0;
        for(int i = 0; i < np; i++){
            for(count = 0; count < grammer[i].length; count++){
                if(grammer[i][count].equals(a)){
                    to_ret += grammer[i][0];
                }
            }
        }
        return to_ret;
    }

    //Makes all possible combinations out of the two string passed
    static String combinat(String a, String b){
        String to_ret = "", temp = "";
        for(int i = 0; i < a.length(); i++){
            for(int j = 0; j < b.length(); j++){
                temp = "";
                temp += a.charAt(i) + "" +  b.charAt(j);
                to_ret += check(temp);
            }
        }
        return to_ret;
    }

    public static String getWordM(){
        return wordM;
    }

    public static boolean isEsGeht() {
        return esGeht;
    }

    public static String[][] run(String word) {
        wordM = word;
        String start = "S";
        //np = no of productions
        np = grammer.length;
        String str = word;
        String st = "";
        String r = "";
        int count;
        String ans_mat[][] = new String[10][10];

        //Fill the diagnol of the matrix (first iteration of algorithm)
        for(int i = 0; i < str.length(); i++){
            r = "";
            st = "" + str.charAt(i);
            for(int j = 0; j < np; j++){
                for(count = 1; count < grammer[j].length; count++){
                    if(grammer[j][count].equals(st)){
                        r += grammer[j][0];
                    }
                }
            }
            ans_mat[i][i] = r;
        }

        //Fill the rest of the matrix
        for(int i = 1; i < str.length(); i++){
            for(int j = i; j < str.length(); j++){
                r = "";
                for(int k = j - i; k < j; k++){
                    r += combinat(ans_mat[j - i][k], ans_mat[k + 1][j]);
                }
                ans_mat[j - i][j] = r;
            }
        }

        //The last column of first row should have the start symbol
        if(ans_mat[0][str.length() - 1].indexOf(start) >= 0){
            System.out.println("Слово подходит к данной грамматике");
            esGeht = true;
        }
        else{
            System.out.println("Слово не подходит к данной грамматике");
            esGeht = false;
        }

        for (int x = 0; x < ans_mat.length; x++){
            int countOfNulls = 0;
            for (int i = 0; i < ans_mat[x].length; i++){
                if(ans_mat[x][i] == null){
                    ++countOfNulls;
                }else{
                    ans_mat[x][i-countOfNulls] = ans_mat[x][i];
                    if(x > 0){
                        ans_mat[x][i] = null;
                    }
                }
            }
        }

        String[][] values = new String[10][10];
        for(int i = 0; i < ans_mat.length;  i++){
            for(int z = 0; z < ans_mat[i].length; z++){
                if(ans_mat[i][z] != null){
                    StringBuilder sb = new StringBuilder();
                    ans_mat[i][z].chars().distinct().forEach(c -> sb.append((char) c));
                    values[z][i] = sb.toString();
                }else{
                    values[z][i] = ans_mat[i][z];
                }
            }
        }

        for (String[] strings : values){
            for (String string : strings){
                if (string == null){
                    System.out.print(" ");
                }else if(string.equals("")){
                    System.out.print("-");
                }else {
                    System.out.print(string);
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        return values;
    }
}