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

    // checked !!
    public static Instances preProcessData(Instances data) {
        int nbAttributs = data.numAttributes();
        ArrayList<Instance> instances = new ArrayList<>(data);

        for(Instance inst:instances){
            if(!inst.hasMissingValue()) continue;
            for(int i=0;i<nbAttributs;i++){
                if(inst.isMissing(i)){
                    if(inst.attribute(i).isNumeric()){
                        System.out.println(" ------------------> value replace "+inst.value(i));
                        inst.attribute(i).setWeight(replaceMissingNumeric(inst,inst.attribute(i),instances));
                    }else{
                        inst.setValue(i,modeNomForClass(instances,inst.attribute(i),getClassOfInstance(inst)));
                        System.out.println(" ------------------> value replace "+inst.stringValue(i));
                    }
                }
            }
        }
        return data;
    }


    public static String modeNomForClass(ArrayList<Instance> instances , Attribute attribut, String clas){
        // For count the frequent of each Attribut's value < Nominal and Date >
        TreeMap<String,Integer> valsFreq = new TreeMap<>() ;

        String nominalVal ; Integer freq ;

        /** Mode For  Numeric Type !! **/
        for(Instance inst:instances){

            // ignor this inst if :
            // 1) missing val of att in this instance
            // 2) this instance class dont equals to clas
            if(inst.isMissing(attribut)) continue;
            if(!appartienClss(inst,clas)) continue ;

            nominalVal = inst.stringValue(attribut);
            if(valsFreq.containsKey(nominalVal)){
                freq = valsFreq.get(nominalVal)+1;
                valsFreq.put(nominalVal,freq);
            }else{
                valsFreq.put(nominalVal,1);
            }
        }

        if(valsFreq.size() == 0) return "";
        return valsFreq.firstKey();
    }




    public static Double replaceMissingNumeric(Instance instance , Attribute attribut ,  ArrayList<Instance> instances){
        if(missingClass(instance)) return modeNumForClass(instances,attribut,null);
        else{
            String clas ;
            Attribute instanceClass  = instance.classAttribute();
            if(instanceClass.isNumeric()) clas = Double.toString(instance.value(instanceClass));
            else clas = instance.stringValue(instanceClass);

            return modeNumForClass(instances,attribut,clas);
        }
    }

    public static Double modeNumForClass(ArrayList<Instance> instances , Attribute attribut, String clas){
        // For count the frequent of each Attribut's value Numeric
        TreeMap<Double,Integer> valsFreq = new TreeMap<>() ;

        Double numVal ; Integer freq ;

        boolean isClassMissing ;
        /** Mode For  Numeric Type !! **/
        for(Instance inst:instances){

            // ignor this inst if :
            // 1) this instance non classed
            // 2) val of att in this instance missing
            // 3) this instance class dont equals to clas sence cals not null
            if(missingClass(inst)) continue ;
            if(inst.isMissing(attribut)) continue;
            if(!appartienClss(inst,clas)) continue ;

            //System.out.println("Entreeeeeeeeee !!!!");
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




    public static String getClassOfInstance(Instance instance){
        String clas = "missing";
        Attribute classAtt = instance.attribute(instance.numAttributes()-1);
        if(classAtt.name().equalsIgnoreCase("class")){
            if(classAtt.isNumeric()) clas = Double.toString(instance.value(classAtt));
            else clas = instance.stringValue(classAtt);
        }
        return clas;
    }

    public static boolean missingClass(Instance intance){
        boolean missing = false;
        //try{ missing = intance.classIsMissing(); }finally { return  missing;}
        return missing;
    }
    public static boolean appartienClss(Instance instance , String class2){
        String class1 = getClassOfInstance(instance);

        if( class2 == null || class1.equals("missing") || class2.equals("missing")) return true ;
        return class1.equalsIgnoreCase(class2);
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
