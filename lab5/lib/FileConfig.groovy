package lib

abstract class FileConfig {

    static String suffix = "arff"
    static String DEFAULT = "Default"
    static String KEEP_WORDS_100 = "KeepWords100"
    static String DO_NOT_OPERATE_PER_CLASS = "DoNotOperatePerClass"
    static String TRANSFORMS = "Transforms"
    static String NORMALIZE_DOC_LENGTH = "NormalizeDocLength"
    static String STEMMER = "Stemmer"
    static String STOP_WORDS = "StopWords"
    static String TOKENIZER = "Tokenizer"
    static String MIN_TERM_PRUNED = "MinTermPruned"
    
    String srcDir
    String srcFile

    def getSourceFile() {
	return "${this.srcDir}/${this.srcFile}.${FileConfig.suffix}"
    }

    def getOutFile(String suffix) {
	return "${this.srcDir}/${this.srcFile}_${suffix}.${FileConfig.suffix}"
    }

    def getFilterLabels() {
	def labels = [
	    this.DEFAULT,
	    this.KEEP_WORDS_100,
	    this.DO_NOT_OPERATE_PER_CLASS,
	    this.TRANSFORMS,
	    this.NORMALIZE_DOC_LENGTH,
	    this.STEMMER,
	    this.TOKENIZER,
	    this.MIN_TERM_PRUNED
	]
	return labels
    }
}
