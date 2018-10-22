package DMweKa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import weka.core.*;


public class AttributDataSet {

    static String[] types = {"numeric","nominal","string","date","relational"};


    private String name ;
    private Attribute attribut;
    private AttributeStats stats ;
    private ArrayList<Instance> instances ;

    /** one day maybe !! xD */
    //private ProtectedProperties properties;

    private int distinct ;
    private double  max , min , Q1 , mean , Q3 , midRange  , weight , sum ;

    private String mode;

    private boolean isNumeric = false;

    AttributDataSet(Attribute attribut, AttributeStats stats , Instances dataSet) {

        this.name = attribut.name();
        this.attribut = attribut;
        this.stats = stats;
        this.instances = new ArrayList<>(dataSet);

        distinct = stats.distinctCount;
        weight   = attribut.weight();


        // spetial for the numeric attributs !!
        if(type().equals("numeric")) {
            isNumeric = true;
            max      = stats.numericStats.max;
            min      = stats.numericStats.min;
            midRange = (max + min) / 2;
            mean     = stats.numericStats.mean;
            sum      = stats.numericStats.sum;
            CalculateQ123(dataSet);
            System.out.println("Q1 ---------------> "+Q1);
        }
        calculeMode();
    }

    public String name() {
        return name;
    }

    public String type() {
        return types[attribut.type()];
    }

    public String distinct() {
        return ""+distinct;
    }

    public String weight() {
        return ""+weight;
    }

    public String max(){
        return ""+max;
    }

    public String min(){
        return ""+stats.numericStats.min;
    }

    public String mean(){
        return ""+stats.numericStats.mean;
    }

    public String sum(){
        return ""+stats.numericStats.sum;
    }

    public String Q1(){
        return ""+Q1;
    }

    public String Q3(){
        return ""+Q3;
    }


    public String mideRange(){
        return ""+midRange;
    }


    public String mode(){
        return ""+mode;
    }


    protected void CalculateQ123(Instances instances) {
        double[] values;
        int  half , quarter;
        double Q2;

        // sort attribute data
        values = instances.attributeToDoubleArray(attribut.index());
        Arrays.sort(values);

        // determine indices
        half    = values.length / 2;
        quarter = half / 2;

        if (values.length % 2 == 1) Q2 = values[half];
        else Q2 = (values[half] + values[half + 1]) / 2;


        if (half % 2 == 1) {
            Q1 = values[quarter];
            Q3 = values[values.length - quarter - 1];
        }
        else {
            Q1 = (values[quarter] + values[quarter + 1]) / 2;
            Q3 = (values[values.length - quarter - 1] + values[values.length - quarter]) / 2;
        }

    }


    private void calculeMode(){
        // For count the frequent of each Attribut's value < Nominal , Numeric and Date >
        TreeMap<Double,Integer> valsFreq = new TreeMap<>() ;
        TreeMap<String,Integer> nominalFreq = new TreeMap<>() ;

        Double numVal ; String nominalVal ; Integer freq ;

        if(isNumeric){
            /** Mode For  Numeric Type !! **/
            for(Instance inst:instances){
                numVal = inst.value(attribut);
                if(valsFreq.containsKey(numVal)){
                    freq = valsFreq.get(numVal)+1;
                    valsFreq.put(numVal,freq);
                }else{
                    valsFreq.put(numVal,1);
                }
            }
        }else{
            /** Mode For  Nominal , String , Date and Relational Type !! **/
            for(Instance inst:instances){
                nominalVal = inst.stringValue(attribut);
                if(nominalFreq.containsKey(nominalVal)){
                    freq = nominalFreq.get(nominalVal)+1;
                    nominalFreq.put(nominalVal,freq);
                }else{
                    nominalFreq.put(nominalVal,1);
                }
            }
        }

        if(nominalFreq.size() == valsFreq.size()) return;
        this.mode = (isNumeric) ? ""+valsFreq.firstKey() : nominalFreq.firstKey();
    }


    public void toTableFx(){
        // TODO
    }

}
