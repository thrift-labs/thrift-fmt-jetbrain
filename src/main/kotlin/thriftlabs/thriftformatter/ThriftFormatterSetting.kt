package thriftlabs.thriftformatter

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage


@State(name = "ThriftFormatterSetting", storages = [Storage("ThriftFormatterSetting.xml")])
class ThriftFormatterSetting : PersistentStateComponent<ThriftFormatterSetting.State> {

    var indentSize: Int = 4
    var patchRequired: Boolean = true
    var patchSeparator: Boolean = true
    var alignByAssign: Boolean = false
    var alignByField: Boolean = false

    data class State(
        var indentSize: Int = 4,
        var patchRequired: Boolean = true,
        var patchSeparator: Boolean = true,
        var alignByAssign: Boolean = false,
        var alignByField: Boolean = false,
    )

    override fun getState(): State {
        return State(indentSize, patchRequired, patchSeparator, alignByAssign, alignByField)
    }

    override fun loadState(state: State) {
        indentSize = state.indentSize
        patchRequired = state.patchRequired
        patchSeparator = state.patchSeparator
        alignByAssign = state.alignByAssign
        alignByField = state.alignByField
    }
}
