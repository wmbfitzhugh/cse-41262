import weka.classifiers.bayes.NaiveBayes
import weka.classifiers.trees.J48
import weka.classifiers.Evaluation
import lib.Utils

import lib.BatchJob

def theDir = "/adjust/to/your/path"
def dataSource = "ReutersCorn-train"

def batchJob = new BatchJob(
    srcDir: theDir,
    srcFile: dataSource,
    classIdx: 0
)
batchJob.run()
