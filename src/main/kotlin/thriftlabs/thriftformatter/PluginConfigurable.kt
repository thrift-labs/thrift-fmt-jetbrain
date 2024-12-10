package thriftlabs.thriftformatter

import com.intellij.openapi.components.service
import com.intellij.openapi.options.Configurable
import java.awt.Component
import java.awt.Dimension
import javax.swing.*

class PluginConfigurable : Configurable {
    private var mainPanel: JPanel? = null
    private var indentSizeField: JTextField? = null
    private var patchRequiredCheckBox: JCheckBox? = null
    private var patchSeparatorCheckBox: JCheckBox? = null
    private var alignByAssignCheckBox: JCheckBox? = null
    private var alignByFieldCheckBox: JCheckBox? = null

    override fun createComponent(): JComponent? {
        mainPanel = JPanel()
        mainPanel!!.layout = BoxLayout(mainPanel, BoxLayout.Y_AXIS)

        val textLabel = JLabel("Indent Size:")
        val textField = JTextField(10)
        textField.preferredSize = Dimension(80, 20)
        textField.maximumSize = Dimension(80, 20)
        indentSizeField = textField

        textLabel.alignmentX = Component.LEFT_ALIGNMENT
        indentSizeField!!.alignmentX = Component.LEFT_ALIGNMENT

        mainPanel!!.add(textLabel)
        mainPanel!!.add(indentSizeField)

        patchRequiredCheckBox = JCheckBox("Patch missed required")
        patchSeparatorCheckBox = JCheckBox("Patch missed Field separator (use ',')")
        alignByAssignCheckBox = JCheckBox("Align Field by '=' ")
        alignByFieldCheckBox = JCheckBox("Align by Field's each part")

        patchRequiredCheckBox!!.alignmentX = Component.LEFT_ALIGNMENT
        patchSeparatorCheckBox!!.alignmentX = Component.LEFT_ALIGNMENT
        alignByFieldCheckBox!!.alignmentX = Component.LEFT_ALIGNMENT
        alignByAssignCheckBox!!.alignmentX = Component.LEFT_ALIGNMENT

        mainPanel!!.add(patchRequiredCheckBox)
        mainPanel!!.add(patchSeparatorCheckBox)
        mainPanel!!.add(alignByFieldCheckBox)
        mainPanel!!.add(alignByAssignCheckBox)

        return mainPanel
    }

    override fun isModified(): Boolean {
        val settings = service<ThriftFormatterSetting>()
        return indentSizeField?.text?.toInt() != settings.indentSize ||
                patchRequiredCheckBox?.isSelected != settings.patchRequired ||
                patchSeparatorCheckBox?.isSelected != settings.patchSeparator ||
                alignByAssignCheckBox?.isSelected != settings.alignByAssign ||
                alignByFieldCheckBox?.isSelected != settings.alignByField
    }

    override fun apply() {
        val settings = service<ThriftFormatterSetting>()
        settings.indentSize = indentSizeField?.text?.toInt() ?: 4
        settings.patchRequired = patchRequiredCheckBox?.isSelected ?: true
        settings.patchSeparator = patchSeparatorCheckBox?.isSelected ?: true
        settings.alignByAssign = alignByAssignCheckBox?.isSelected ?: true
        settings.alignByField = alignByFieldCheckBox?.isSelected ?: true
    }

    override fun getDisplayName(): String {
        return "Thrift Formatter Settings1"
    }

    override fun reset() {
        val settings = service<ThriftFormatterSetting>()
        indentSizeField?.text = settings.indentSize.toString()
        patchRequiredCheckBox?.isSelected  = settings.patchRequired
        patchSeparatorCheckBox?.isSelected  = settings.patchSeparator
        alignByAssignCheckBox?.isSelected  = settings.alignByAssign
        alignByFieldCheckBox?.isSelected  = settings.alignByField
    }
}
