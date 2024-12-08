package thriftlabs.thriftformatter

import com.intellij.formatting.service.AsyncDocumentFormattingService
import com.intellij.formatting.service.AsyncFormattingRequest
import com.intellij.formatting.service.FormattingService
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.psi.PsiFile

import thriftlabs.thriftfmt.Option
import thriftlabs.thriftfmt.ThriftFormatter
import thriftlabs.thriftparser.Thrift

class ThriftFormatterService: AsyncDocumentFormattingService() {
    override fun getFeatures(): MutableSet<FormattingService.Feature> {
        val features = mutableSetOf<FormattingService.Feature>()
        return features
    }

    override fun canFormat(file: PsiFile): Boolean {
        return file.name.endsWith(".thrift")
    }

    override fun createFormattingTask(formattingRequest: AsyncFormattingRequest): FormattingTask? {
        val filename = formattingRequest.context.containingFile.name;
        val text = formattingRequest.documentText;
        val data = Thrift.parse(text);
        if (!data.isSuccess) {
            println("Error: Parsing failed for file: $filename")
            notifyUser("Parsing failed for file: $filename")
            return null;
        }

        val opt = Option();
        val formatter = ThriftFormatter(data, opt)
        val fmtText = formatter.format()
        if (fmtText == text) {
            notifyUser("no need format: $filename")
            return null;
        }

        return object : FormattingTask {
            override fun run() {
                formattingRequest.onTextReady(fmtText)
                notifyUser("Success format done: $filename")
            }
            override fun cancel(): Boolean {
                return false
            }
        }
    }

    override fun getNotificationGroupId(): String {
        return "ThriftFormatterNotificationGroup"
    }

    override fun getName(): String {
        return "Thrift Formatter"
    }

    private fun notifyUser(message: String) {
        val notification = Notification(
            notificationGroupId,
            name,
            message,
            NotificationType.INFORMATION
        )
        Notifications.Bus.notify(notification)
    }
}
