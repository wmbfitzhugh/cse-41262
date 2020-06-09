package lib

import lib.FileConfig
import weka.filters.Filter
import weka.core.Instances
import weka.core.SelectedTag
import weka.core.stemmers.LovinsStemmer
import weka.core.tokenizers.CharacterNGramTokenizer
import weka.core.converters.ConverterUtils.DataSource
import weka.core.stopwords.Rainbow
import weka.filters.unsupervised.attribute.StringToWordVector
import weka.core.converters.ArffSaver


class DataPrep extends FileConfig {

    static def createDataSet(Filter aFilter, String inFile, String outFile) {
	def dataSource = DataSource.read(inFile)
	dataSource.setClassIndex(dataSource.numAttributes() - 1)
    
	aFilter.setInputFormat(dataSource)
	Instances filtered = Filter.useFilter(dataSource, aFilter)
    
	println("Saving dataSet to ${outFile}")
    
	ArffSaver saver = new ArffSaver()
    
	saver.setInstances(filtered)
	saver.setFile(new File(outFile))
	saver.writeBatch()
    }
    
    def prep() {
	// Steps 2-4
	// default filter
	def defaultFilter = new StringToWordVector()
	createDataSet(defaultFilter, this.getSourceFile(), this.getOutFile(this.DEFAULT))
	
	// Steps 5-7
	// Set Words to Keep to 100
	def keepWords100Filter = new StringToWordVector(100)
	createDataSet(keepWords100Filter, this.getSourceFile(), this.getOutFile(this.KEEP_WORDS_100))

	// Step 8
	// DoNotOperateonPerClassBasis
	def doNotOperateonPerClassFilter = new StringToWordVector()
	doNotOperateonPerClassFilter.setDoNotOperateOnPerClassBasis(true)
	createDataSet(doNotOperateonPerClassFilter, this.getSourceFile(), this.getOutFile(this.DO_NOT_OPERATE_PER_CLASS))

	// Step 9
	// IDFTTransform and TFTTransform to True, OutputWordCount to True
	def transformsFilter = new StringToWordVector()
	transformsFilter.setIDFTransform(true)
	transformsFilter.setTFTransform(true)
	transformsFilter.setOutputWordCounts(true)
	createDataSet(transformsFilter, this.getSourceFile(), this.getOutFile(this.TRANSFORMS))

	// Step 10
	// Utilize NormilizeDocLength
	def normalizeDocLengthFilter = new StringToWordVector()
	normalizeDocLengthFilter.setNormalizeDocLength(new SelectedTag(StringToWordVector.FILTER_NORMALIZE_ALL, StringToWordVector.TAGS_FILTER))
	createDataSet(normalizeDocLengthFilter, this.getSourceFile(), this.getOutFile(this.NORMALIZE_DOC_LENGTH))

	// Step 11
	// Use the Stemmer – LovinStemmer
	def stemmerFilter = new StringToWordVector()
	stemmerFilter.setStemmer(new LovinsStemmer())
	createDataSet(stemmerFilter, this.getSourceFile(), this.getOutFile(this.STEMMER))

	// Step 12
	// StopWords – English stop words list
	def stopWordsFilter = new StringToWordVector()
	stopWordsFilter.setStopwordsHandler(new Rainbow())
	createDataSet(stopWordsFilter, this.getSourceFile(), this.getOutFile(this.STOP_WORDS))

	// Step 13
	// Tokenizer – use the ngrams
	def tokenizerFilter = new StringToWordVector()
	tokenizerFilter.setTokenizer(new CharacterNGramTokenizer())
	createDataSet(tokenizerFilter, this.getSourceFile(), this.getOutFile(this.TOKENIZER))

	// Step 14
	//  MinTermFrequency; periodicPruning
	int minTermFreq = 2
	double pruningRate = 0.2
	def minTermPrunedFilter = new StringToWordVector()
	minTermPrunedFilter.setMinTermFreq(minTermFreq)
	minTermPrunedFilter.setPeriodicPruning(pruningRate)
	createDataSet(minTermPrunedFilter, this.getSourceFile(), this.getOutFile(this.MIN_TERM_PRUNED))
    }
}
