package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_ToggleButton_textOffandOn;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecFieldSetFloat;

/**
 * Nota: ToggleButton ha sido reemplazado totalmente por Switch, lo implementamos para los despistados
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_ToggleButton extends ClassDescViewBased
{
    public ClassDescView_widget_ToggleButton(ClassDescViewMgr classMgr, ClassDescView_widget_CompoundButton parentClass)
    {
        super(classMgr,"android.widget.ToggleButton",parentClass);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecFieldSetFloat(this, "disabledAlpha", "mDisabledAlpha", 0.5f));
        addAttrDescAN(new AttrDescView_widget_ToggleButton_textOffandOn(this, "textOff"));
        addAttrDescAN(new AttrDescView_widget_ToggleButton_textOffandOn(this, "textOn"));
    }
}

