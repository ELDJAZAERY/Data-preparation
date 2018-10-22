package DMweKa;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NominalToBinary;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import weka.filters.unsupervised.attribute.Standardize;

import java.util.ArrayList;
import java.util.TreeMap;


public class PreProcessing {


    public static Instances preProcessData(Instances data) {
        int nbAttributs = data.numAttributes();
        ArrayList<Instance> instances = new ArrayList<>(data);

        for(Instance inst:instances){
            if(!inst.hasMissingValue()) continue;
            for(int i=0;i<nbAttributs;i++){
                if(inst.isMissing(i)){
                    if(inst.attribute(i).isNumeric()){
                        inst.attribute(i).setWeight(replaceMissingNumeric(inst,inst.attribute(i),instances));
                    }else{
                        inst.attribute(i).setStringValue(replaceMissingNominal(inst,inst.attribute(i),instances));
                    }
                }
            }
        }

        return data;
    }


    public static Double replaceMissingNumeric(Instance instance , Attribute attribut ,  ArrayList<Instance> instances){

        String clas ;
        Attribute instanceClass  = instance.classAttribute();

        if(instance.classIsMissing()) clas = null;
        else if(instanceClass.isNumeric()) clas = Double.toString(instance.value(instanceClass));
        else clas = instance.stringValue(instanceClass);

        return 0.0;
    }
    public static Double modeNumForClass(ArrayList<Instance> instances , Attribute attribut, String clas){
        // For count the frequent of each Attribut's value < Nominal , Numeric and Date >
        TreeMap<Double,Integer> valsFreq = new TreeMap<>() ;

        Double numVal ; Integer freq ;

        /** Mode For  Numeric Type !! **/
        for(Instance inst:instances){

            // ignor this inst if :
            // 1) this instance non classed
            // 2) val of att in this instance missing
            // 3) this instance class dont equals to clas sence cals not null
            if(inst.classIsMissing() || clas == null ) continue ;
            if(inst.isMissing(attribut)) continue;
            if(!appartienClss(inst,clas)) continue ;

            numVal = inst.value(attribut);
            if(valsFreq.containsKey(numVal)){
                freq = valsFreq.get(numVal)+1;
                valsFreq.put(numVal,freq);
            }else{
                valsFreq.put(numVal,1);
            }
        }

        if(valsFreq.size() == 0) return 0.0;
        return valsFreq.firstKey();
    }




    public static String replaceMissingNominal(Instance instance , Attribute attribut , ArrayList<Instance> instances){
        return "";
    }
    public static String modeNomForClass(String clas){
        return  "";
    }



    public static boolean appartienClss(Instance instance , String class2){
        if(class2 == null ) return true ;
        Attribute class1 = instance.classAttribute();
        if( instance.classAttribute().isNumeric()){
           return Double.toString(instance.value(class1)).equals(class2);
        }else {
            return instance.stringValue(class1).equals(class2);
        }
    }

    public static Instances preProcessDataUsingWekaFiltres(Instances data, boolean shouldImpute,
                                                           boolean shouldStandardize,boolean shouldBinarize) throws Exception {

        if( shouldImpute ) {
            Filter impute = new ReplaceMissingValues();
            impute.setInputFormat(data);
            data = Filter.useFilter(data, impute);
        }
        if( shouldStandardize ) {
            Filter standardize = new Standardize();
            standardize.setInputFormat(data);
            data = Filter.useFilter(data, standardize);
        }
        if( shouldBinarize ) {
            Filter binarize = new NominalToBinary();
            binarize.setInputFormat(data);
            // make resulting binary attrs nominal, not numeric
            binarize.setOptions(new String[] { "-N" } );
            data = Filter.useFilter(data, binarize);
        }
        return data;
    }



}
