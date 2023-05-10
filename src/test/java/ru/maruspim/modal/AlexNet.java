package ru.maruspim.modal;

import java.util.List;

public class AlexNet
{
    public String name;
    public String architecture;
    public Integer year;
    public List<String> authors;
    public String aim;
    public Boolean isNonlinear;
    public Boolean dropoutUsage;
    public String imageResolution;
    public Integer convolutionalLayersNumber;
    public Integer fullyConnectedLayersNumber;
    public Long parametersNumber;
    public String activationFunction;
    public Integer epochs;


    public AlexNet.Dataset dataset;
    public static class Dataset {
        public Long trainingSetSize;
        public Long testingSetSize;
        public Long validationSetSize;
    }
}
