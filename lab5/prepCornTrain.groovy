import lib.DataPrep

def cornTrainPrep = new DataPrep(
    srcDir: "/adjust/to/your/dir",
    srcFile: "ReutersCorn-train"
)
cornTrainPrep.prep()
