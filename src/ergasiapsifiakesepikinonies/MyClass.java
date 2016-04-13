/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ergasiapsifiakesepikinonies;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author georkirm
 */
public class MyClass {
    final private int megethosGenitoraI; //size of devisor
    final private int megethosArxikoMinimaI; //size of starting message
    private int[] einaiToMinimaSostoI; //remain after 2nd division. if 0 the message is without errors
    public int[] stalmenoMinimaXorisErrorsI; //message after CRC without errors
    public int[] stalmenoMinimaMeErrorsI; //message after CRC with errors
    final private int[] genitorasI; //divisor
    final private int[] minimaMeMidenI; //given message before CRC but with 0
    final private String arxikoMinimaS; //given message before CRC given from the random
    double errorRateI; //error rate given from the User
    public MyClass(String arxikoMinimaS, String genitorasS,double errorRateI){
        megethosGenitoraI = genitorasS.length();
        megethosArxikoMinimaI = arxikoMinimaS.length();
        String minimaMeMidenS = arxikoMinimaS;
        this.arxikoMinimaS= arxikoMinimaS;
        this.errorRateI= errorRateI;
        for(int j=1; j<=(megethosGenitoraI); j++){ //give 0 to starting message
            minimaMeMidenS = minimaMeMidenS + "0";
        }
        minimaMeMidenI = pinakopoihsh(minimaMeMidenS); // make the strings array of ints to do the division
        genitorasI = pinakopoihsh(genitorasS);
    }
    
    private int[] minimaAllazeiApoTaErrors(double errorPososto, int[] minima){
         //for every bit i generate a double from 0-1 (*100) and if this is smaller than errorRate then i change that bit
        for(int i= 0; i<=minima.length-1;i++){
            Random rand = new Random();
            //int j = rand.nextInt(100);
            double j = rand.nextDouble();
            j= j*100;
            if(j<errorPososto){
                if(minima[i]==0){
                    minima[i]= 1;
                }else{
                    minima[i]= 0;
                }
            }
        }
        return minima;
    }
    
    public boolean einaiToMinimaSosto(int[] minima){
//        System.out.println("~~~~~~~~~");
//        for (int i = 0; i < minima.length; i++) {
//            System.out.print(minima[i]);
//        }
        for(int i=0 ; i < minima.length ; i++) { //get the remain from the second division. if its not 0 then it sented with errors
            if(minima[i] != 0) {
		return false;
            }
        }
        return true;
    }
    
    public boolean checkerMessager(){
        //check message bit by bit
        for (int i = 0; i <= stalmenoMinimaXorisErrorsI.length-1; i++) {
            if (stalmenoMinimaMeErrorsI[i]==stalmenoMinimaXorisErrorsI[i]) {
                //do nothing until for ends
            }else{
                return false;
            }
        }
        return true;
    }
    
    private int[] pinakopoihsh(String s){
        int apot[] = new int[s.length()]; //make the string array int by spliting every char of it
        String[] helper2 = s.split("(?<=\\G.{1})");
        for(int j=0; j<=(s.length()-1);j++){
            apot[j]= Integer.parseInt(helper2[j]);
        }
        return apot;
    }
    
    public boolean doCRC(){
        int apotelesmaDiereshsI[]= dieresh(minimaMeMidenI, genitorasI); //divide starting message with 0 with the divisor
        String stalmenoMinimaS = dimiourgiaMinimatosPouThaStalthei(arxikoMinimaS,apotelesmaDiereshsI); //put the remain in the starting message before crc
        stalmenoMinimaXorisErrorsI = pinakopoihsh(stalmenoMinimaS); //make it int array
        stalmenoMinimaMeErrorsI= new int[stalmenoMinimaXorisErrorsI.length];
        System.arraycopy(stalmenoMinimaXorisErrorsI, 0, stalmenoMinimaMeErrorsI, 0, stalmenoMinimaXorisErrorsI.length); 
        stalmenoMinimaMeErrorsI= minimaAllazeiApoTaErrors(errorRateI, stalmenoMinimaMeErrorsI); //give message after crc errors
        int[] helper0 = new int[stalmenoMinimaMeErrorsI.length+megethosGenitoraI]; //
        for(int i=0;i<=stalmenoMinimaMeErrorsI.length-1;i++){
            helper0[i]=stalmenoMinimaMeErrorsI[i];
        }
        //put 0 in message with errors for do CRC agien and check if it sented without errors
        for(int i=stalmenoMinimaMeErrorsI.length; i<=stalmenoMinimaMeErrorsI.length+megethosGenitoraI-1;i++){
            helper0[i]=0;
        }
        einaiToMinimaSostoI= dieresh(helper0, genitorasI);
        return einaiToMinimaSosto(einaiToMinimaSostoI); //return boolean if message was sented with errors or not
    }
    
    private int[] dieresh(int minimaMeMidenI[], int genitorasI[]){
        int upolipoI[] = new int[genitorasI.length];
        for(int j=0; j<=genitorasI.length-1; j++){ //give the remain the first ints of the message
            upolipoI[j]=minimaMeMidenI[j];
        }
        for(int i=0; i<megethosArxikoMinimaI; i++){ //for every bit
            if(upolipoI[0]==1){ //if first int of array is 1 then xor
                for(int j=1; j<=genitorasI.length-1;j++){ 
                    if(upolipoI[j]==genitorasI[j]){
                        upolipoI[j-1]=0;
                    }else{
                        upolipoI[j-1]=1;
                    }
                }
            }else{ //if first int of upolipoI is 0 then move the bits in front
                for(int j=1; j<=genitorasI.length-1;j++){
                    if(upolipoI[j]==0){ 
                        upolipoI[j-1]=0;
                    }else{
                        upolipoI[j-1]=1;
                    }
                }
            }
            upolipoI[genitorasI.length-1] = minimaMeMidenI[i+genitorasI.length]; //get value of next ints of message
//            for(int ii=0;ii<=genitorasI.length-1;ii++){
//            }
        }
        return upolipoI;
    }
    
    private String dimiourgiaMinimatosPouThaStalthei(String arxikoMinima,int[] upolipo){
        for(int i=0; i<=(upolipo.length-2); i++){ //give starting mesage the values of the remain of the divide
            arxikoMinima=arxikoMinima + Integer.toString(upolipo[i]);
        }
        return arxikoMinima;
    }
}
