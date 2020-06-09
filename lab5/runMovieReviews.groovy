import weka.classifiers.bayes.NaiveBayes
import weka.classifiers.trees.J48
import weka.classifiers.Evaluation
import lib.Utils

import lib.BatchJob

def theDir = "/adjust/to/your/dir"
def moviesDefault = "Movie_Reviews"

def batchJob = new BatchJob(
    srcDir: theDir,
    srcFile: moviesDefault,
    classIdx: 0
)
batchJob.run()
