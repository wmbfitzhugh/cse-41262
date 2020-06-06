import weka.filters.Filter
import weka.core.Instances
import weka.core.SelectedTag
import weka.core.stemmers.LovinsStemmer
import weka.core.tokenizers.CharacterNGramTokenizer
import weka.core.converters.ConverterUtils.DataSource
import weka.core.stopwords.Rainbow
import weka.filters.unsupervised.attribute.StringToWordVector
import weka.core.converters.ArffSaver

srcDir = "/some/path"
moviesFile = "${srcDir}/Movie_Reviews.arff"

println("Using srcDir=${srcDir}, moviesFile=${moviesFile}")


def createDataSet(Filter aFilter, String inFile, String outFile) {
    
    dataSource = DataSource.read(inFile)
    dataSource.setClassIndex(dataSource.numAttributes() - 1)
    
    aFilter.setInputFormat(dataSource)
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

// Step 9
// IDFTTransform and TFTTransform to True, OutputWordCount to True
transformsFilter = new StringToWordVector()
transformsFilter.setIDFTransform(true)
transformsFilter.setTFTransform(true)
transformsFilter.setOutputWordCounts(true)
transformsOut = "${srcDir}/Movie_Reviews_transforms.arff"
createDataSet(transformsFilter, moviesFile, transformsOut)

// Step 10
// Utilize NormilizeDocLength
normalizeDocLengthFilter = new StringToWordVector()
normalizeDocLengthFilter.setNormalizeDocLength(new SelectedTag(StringToWordVector.FILTER_NORMALIZE_ALL, StringToWordVector.TAGS_FILTER))
normalizedDocLengthOut = "${srcDir}/Movie_Reviews_normalizeDocLength.arff"
createDataSet(normalizeDocLengthFilter, moviesFile, normalizedDocLengthOut)

// Step 11
// Use the Stemmer – LovinStemmer
stemmerFilter = new StringToWordVector()
stemmerFilter.setStemmer(new LovinsStemmer())
stemmerOut = "${srcDir}/Movie_Reviews_stemmer.arff"
createDataSet(stemmerFilter, moviesFile, stemmerOut)

// Step 12
// StopWords – English stop words list
stopWordsFilter = new StringToWordVector()
stopWordsFilter.setStopwordsHandler(new Rainbow())
stopWordsOut = "${srcDir}/Movie_Reviews_stopWords.arff"
createDataSet(stopWordsFilter, moviesFile, stopWordsOut)

// Step 13
// Tokenizer – use the ngrams
tokenizerFilter = new StringToWordVector()
tokenizerFilter.setTokenizer(new CharacterNGramTokenizer())
tokenizerOut = "${srcDir}/Movie_Reviews_tokenizer.arff"
createDataSet(tokenizerFilter, moviesFile, tokenizerOut)

// Step 14
//  MinTermFrequency; periodicPruning
int minTermFreq = 2
double pruningRate = 0.2
minTermPrunedFilter = new StringToWordVector()
minTermPrunedFilter.setMinTermFreq(minTermFreq)
minTermPrunedFilter.setPeriodicPruning(pruningRate)
minTermPrunedOut = "${srcDir}/Movie_Reviews_minTermPruned.arff"
createDataSet(minTermPrunedFilter, moviesFile, minTermPrunedOut)