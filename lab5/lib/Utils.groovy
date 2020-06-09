package lib

import weka.core.converters.ConverterUtils.DataSource
import weka.core.Attribute

class Utils {

    static getDataset(String filePath, int classIdx) {
	def dataSource = DataSource.read(filePath)
	println("setting class on ${filePath} to ${classIdx}")
	dataSource.setClassIndex(classIdx)
	return dataSource
    }

}
