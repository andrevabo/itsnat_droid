package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_RadioGroup_checkedButton;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_RadioGroup_orientation;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_RadioGroup extends ClassDescViewBased
{
    public ClassDescView_widget_RadioGroup(ClassDescViewMgr classMgr,ClassDescView_widget_LinearLayout parentClass)
    {
        super(classMgr,"android.widget.RadioGroup",parentClass);
    }

    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescView_widget_RadioGroup_checkedButton(this));
        addAttrDescAN(new AttrDescView_widget_RadioGroup_orientation(this)); // Se usa internamente pero no está documentado
    }
}

