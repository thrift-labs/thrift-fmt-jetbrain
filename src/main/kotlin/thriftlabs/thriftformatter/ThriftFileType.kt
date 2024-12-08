package thriftlabs.thriftformatter

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon


object ThriftFileType : LanguageFileType(ThriftLanguage) {
    override fun getName() = "Thrift"
    override fun getDescription() = "Thrift files"
    override fun getDefaultExtension() = "thrift"
    override fun getIcon(): Icon? = null
}
