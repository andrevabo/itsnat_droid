package org.itsnat.droid.impl.xmlinflater.layout.classtree;

import org.itsnat.droid.impl.xmlinflater.layout.ClassDescViewMgr;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_ExpandableListView_childIndicatorLeft;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_ExpandableListView_childIndicatorRight;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_ExpandableListView_indicatorLeft;
import org.itsnat.droid.impl.xmlinflater.layout.attr.widget.AttrDescView_widget_ExpandableListView_indicatorRight;
import org.itsnat.droid.impl.xmlinflater.shared.attr.AttrDescReflecMethodDrawable;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescView_widget_ExpandableListView extends ClassDescViewBased
{
    public ClassDescView_widget_ExpandableListView(ClassDescViewMgr classMgr,ClassDescView_widget_ListView parentClass)
    {
        super(classMgr,"android.widget.ExpandableListView",parentClass);
    }

    @SuppressWarnings("unchecked")
    protected void init()
    {
        super.init();

        addAttrDescAN(new AttrDescReflecMethodDrawable(this, "childDivider", "@null")); // Android tiene un Drawable por defecto
        addAttrDescAN(new AttrDescReflecMethodDrawable(this, "childIndicator", "@null"));
        // android:childIndicatorEnd es Level 18
        addAttrDescAN(new AttrDescView_widget_ExpandableListView_childIndicatorLeft(this));
        addAttrDescAN(new AttrDescView_widget_ExpandableListView_childIndicatorRight(this));
        // android:childIndicatorStart es Level 18
        addAttrDescAN(new AttrDescReflecMethodDrawable(this, "groupIndicator", "@null")); // Android tiene un Drawable por defecto
        // android:indicatorEnd es Level 18
        addAttrDescAN(new AttrDescView_widget_ExpandableListView_indicatorLeft(this));
        addAttrDescAN(new AttrDescView_widget_ExpandableListView_indicatorRight(this));
        // android:indicatorStart es Level 18
    }
}

