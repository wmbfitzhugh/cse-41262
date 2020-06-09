package lib

import weka.classifiers.trees.J48
import weka.classifiers.bayes.NaiveBayes
import weka.classifiers.functions.SMO
import weka.classifiers.rules.JRip
import weka.classifiers.Evaluation
import lib.Utils
import lib.FileConfig


class BatchJob extends FileConfig {

    int classIdx

    def getClassifierBatch() {
	// Get collection of labelled classifiers
	def nb = new Tuple2("Naive Bayes", new NaiveBayes())
	def j48 = new Tuple2("J48", new J48())
	def svm = new Tuple2("SVM", new SMO())
	def jRip = new Tuple2("JRip", new JRip())

	return [
	    nb, j48, svm, jRip
	]
    }
    
    def evaluateModel(classifier, String filePath, String title) {
	def dataSet = Utils.getDataset(filePath, this.classIdx)
	classifier.buildClassifier(dataSet)

	Evaluation eval = new Evaluation(dataSet)
	eval.crossValidateModel(classifier, dataSet, 10, new Random(1))
	println(eval.toSummaryString(title, false))
	println(eval.toMatrixString())

	def percentCorrect = (eval.correct() / (eval.correct() + eval.incorrect())) * 100
	println("${percentCorrect} correctly classified instances")
	
	return percentCorrect
    }

    def run() {

	def results = [:]
	this.getFilterLabels().each { filter ->
	    def classifiers = this.getClassifierBatch()
	    def filteredDS = this.getOutFile(filter)
	    def res = classifiers.each { 
		def (label, classifier) = it
		def percentCorrect = this.evaluateModel(
		    classifier,
		    filteredDS,
		    "========== ${this.srcFile}_${filter}.${this.suffix} - ${label} ========"
		)
		def key = new Tuple2(filter, label)
		results.put(key, percentCorrect)
	    }
	}

	def headers = ["DataSet".padRight(50)] + this.getClassifierBatch().collect { "${it.first}".center(20) }
	println(headers.join(" | "))

	def rows = this.getFilterLabels().collect { f ->
	    def dataSet = "${this.srcFile}_${f}.${this.suffix}"
	    def vals = this.getClassifierBatch().collect { c ->
		def (label, _) = c
		def key = new Tuple2(f, label)
		def value = results.get(key)
		return String.format ("%,.3f", value).center(20)
	    }
	    return [dataSet.padRight(50)] + vals
	}
	rows.each { println(it.join(" ")) }
    }
}

