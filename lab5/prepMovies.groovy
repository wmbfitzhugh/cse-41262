import weka.filters.Filter
import weka.core.Instances
import weka.core.converters.ConverterUtils.DataSource
import weka.filters.unsupervised.attribute.StringToWordVector
import weka.core.converters.ArffSaver

srcDir = "/Users/bfitz/Documents/TextMining/MovieReviews"
moviesFile = "${srcDir}/Movie_Reviews.arff"

println("Using srcDir=${srcDir}, moviesFile=${moviesFile}")


def createDataSet(Filter aFilter, String inFile, String outFile) {
    
    dataSource = DataSource.read(inFile)
    dataSource.setClassIndex(movies.numAttributes() - 1)
    
    aFilter.setInputFormat(movies)
    Instances filtered = Filter.useFilter(dataSource, aFilter)
    
    println("Saving dataSet to ${outFile}")
    
    ArffSaver saver = new ArffSaver()
    
    saver.setInstances(filtered)
    saver.setFile(new File(outFile))
    saver.writeBatch()
}

// Steps 2-4
// default filter
defaultFilter = new StringToWordVector()
defaultFiltered = "${srcDir}/Movie_Reviews_Default.arff"
createDataSet(defaultFilter, moviesFile, defaultFiltered)

// Steps 5-7
// Set Words to Keep to 100
keepWords100Filter = new StringToWordVector(100)
keepWords100Filtered = "${srcDir}/Movie_Reviews_KeepWords100.arff"
createDataSet(keepWords100Filter, moviesFile, keepWords100Filtered)

// Step 8
// DoNotOperateonPerClassBasis
doNotOperateonPerClassFilter = new StringToWordVector()
doNotOperateonPerClassFilter.setDoNotOperateOnPerClassBasis(true)
doNotOperateonPerClassFilterOut = "${srcDir}/Movie_Reviews_doNotOperatePerClass.arff"
createDataSet(doNotOperateonPerClassFilter, moviesFile, doNotOperateonPerClassFilterOut)

