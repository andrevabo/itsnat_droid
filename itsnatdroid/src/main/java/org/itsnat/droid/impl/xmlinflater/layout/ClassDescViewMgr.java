package org.itsnat.droid.impl.xmlinflater.layout;

import android.inputmethodservice.KeyboardView;
import android.view.View;

import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.impl.browser.serveritsnat.NodeToInsertImpl;
import org.itsnat.droid.impl.dom.DOMAttr;
import org.itsnat.droid.impl.dom.layout.DOMElemView;
import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.ClassDescMgr;
import org.itsnat.droid.impl.xmlinflater.XMLInflaterRegistry;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewBased;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescViewUnknown;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_Include;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_gesture_GestureOverlayView;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_view_View;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_view_ViewGroup;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_view_ViewStub;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_AbsListView;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_AbsSeekBar;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_AbsSpinner;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_AdapterViewAnimator;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_AdapterViewFlipper;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_AnalogClock;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_AutoCompleteTextView;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_CalendarView;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_CheckedTextView;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_Chronometer;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_CompoundButton;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_DatePicker;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_ExpandableListView;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_FrameLayout;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_Gallery;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_GridLayout;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_GridView;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_HorizontalScrollView;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_ImageView;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_LinearLayout;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_ListView;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_ProgressBar;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_RadioGroup;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_RatingBar;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_RelativeLayout;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_ScrollView;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_SearchView;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_SeekBar;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_SlidingDrawer;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_Spinner;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_Switch;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_TabWidget;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_TableLayout;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_TextView;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_ToggleButton;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_ViewAnimator;
import org.itsnat.droid.impl.xmlinflater.layout.classtree.ClassDescView_widget_ViewFlipper;

/**
 * Created by jmarranz on 30/04/14.
 */
public class ClassDescViewMgr extends ClassDescMgr<ClassDescViewBased>
{
    public final String[] implicitImports = new String[] { "android.view.", "android.widget." };  // Es para añadir imports implícitos tal y como hace Android en los layouts

    public ClassDescViewMgr(XMLInflaterRegistry parent)
    {
        super(parent);
        initClassDesc();
    }

    public ClassDescViewBased get(NodeToInsertImpl node)
    {
        String className = node.getName();
        if ("view".equals(className))
        {
            DOMAttr classAttr = node.getDOMAttribute(null, "class");
            if (classAttr == null) throw new ItsNatDroidException("Expecified <view> but missing class attribute");
            String classAttrValue = classAttr.getValue();
            return get(className,classAttrValue);
        }
        return get(className,null);
    }

    public ClassDescViewBased get(DOMElemView domElemView)
    {
        String className = domElemView.getTagName();
        if ("view".equals(className))
        {
            DOMAttr classAttr = domElemView.getDOMAttribute(null, "class");
            if (classAttr == null) throw new ItsNatDroidException("Expecified <view> but missing class attribute");
            String classAttrValue = classAttr.getValue();
            return get(className,classAttrValue);
        }
        return get(className,null);
    }

    @Override
    public ClassDescViewBased get(String className)
    {
        return get(className,null);
    }

    private ClassDescViewBased get(String className,String classAttrValue)
    {
        if (classAttrValue == null)
        {
            ClassDescViewBased classDesc = classes.get(className);
            if (classDesc != null)
                return classDesc;
            if (isSimpleClassName(className))
            {
                for (String implicit : implicitImports)
                {
                    String classNameCandidate = implicit + className;
                    classDesc = classes.get(classNameCandidate);
                    if (classDesc != null)
                        return classDesc;
                }
                // Hay algún caso como CheckBox que no está registrado en ItsNat Droid porque NO tiene atributos específicos (la clase base sí), luego al llamarse a resolveClass() se intentará de nuevo cargar
                // usando los implicit imports pues es posible que exista (puede ser un componente no existente en API 15 pero sí en superiores)
                // También puede ser un custom view con package por defecto (sin package)
            }
        }
        else if ("view".equals(className))
        {
            // Caso <view class="className">
            className = classAttrValue;
        }
        else throw MiscUtil.internalError();

        Class<? extends View> nativeClass = null;
        try { nativeClass = resolveClass(className); }
        catch (ClassNotFoundException ex) { throw new ItsNatDroidException(ex); }
        ClassDescViewBased classDesc = get(nativeClass);
        return classDesc;
    }

    public ClassDescViewBased get(Class<? extends View> nativeClass)
    {
        ClassDescViewBased classDesc = classes.get(nativeClass.getName());
        if (classDesc == null) classDesc = registerUnknown(nativeClass);
        return classDesc; // Nunca es nulo
    }

    public ClassDescViewBased get(View nativeObj)
    {
        Class<? extends View> nativeClass = nativeObj.getClass();
        return get(nativeClass);
    }

    private ClassDescViewBased registerUnknown(Class<? extends View> nativeClass)
    {
        String className = nativeClass.getName();
        // Tenemos que obtener los ClassDescViewBase de las clases base para que podamos saber lo más posible y procesar por ej atributos
        @SuppressWarnings("unchecked")
        Class<? extends View> superClass = (Class<? extends View>)nativeClass.getSuperclass();
        ClassDescViewBased parentClassDesc = get(superClass); // Si fuera también unknown se llamará recursivamente de nuevo a este método
        ClassDescViewBased classDesc = createClassDescUnknown(className, parentClassDesc);

        classes.put(nativeClass.getName(), classDesc);

        return classDesc;
    }


    @SuppressWarnings("unchecked")
    private Class<? extends View> resolveClass(String viewName) throws ClassNotFoundException
    {
        if (isSimpleClassName(viewName))
        {
            for(int i = 0; i < implicitImports.length; i++)
            {
                String implicit = implicitImports[i];
                if (i + 1 == implicitImports.length) // El último, si falla dejamos que haya excepción
                {
                    return resolveClass(implicit + viewName);
                }
                else
                {
                    try { return resolveClass(implicit + viewName); }
                    catch (ClassNotFoundException e) { continue; }
                }
            }
            throw MiscUtil.internalError(); // Nunca se llega aqui
        }
        else
        {
            return (Class<? extends View>)Class.forName(viewName);
        }
    }

    private ClassDescViewBased createClassDescUnknown(String className,ClassDescViewBased parentClass)
    {
        return new ClassDescViewUnknown(this,className,parentClass);
    }

    public boolean isSimpleClassName(String className)
    {
        return className.indexOf('.') == -1;
    }

    @Override
    protected void initClassDesc()
    {
        ClassDescView_view_View view_View = new ClassDescView_view_View(this);
        addClassDesc(view_View);

            ClassDescView_widget_AnalogClock widget_AnalogClock = new ClassDescView_widget_AnalogClock(this,view_View);
            addClassDesc(widget_AnalogClock);

            ClassDescView_widget_ImageView widget_ImageView = new ClassDescView_widget_ImageView(this,view_View);
            addClassDesc(widget_ImageView);

                // android.widget.ImageButton no tiene atributos
                    // android.widget.ZoomButton no tiene atributos ni derivadas
            // KeyboardView tiene escaso interés, para el futuro

            // android.app.MediaRouteButton no tiene atributos in derivadas

            ClassDescView_widget_ProgressBar widget_ProgressBar = new ClassDescView_widget_ProgressBar(this,view_View);
            addClassDesc(widget_ProgressBar);
                ClassDescView_widget_AbsSeekBar widget_AbsSeekBar = new ClassDescView_widget_AbsSeekBar(this,widget_ProgressBar);
                addClassDesc(widget_AbsSeekBar);

                    ClassDescView_widget_RatingBar widget_RatingBar = new ClassDescView_widget_RatingBar(this,widget_AbsSeekBar);
                    addClassDesc(widget_RatingBar);
                    ClassDescView_widget_SeekBar widget_SeekBar = new ClassDescView_widget_SeekBar(this,widget_AbsSeekBar);
                    addClassDesc(widget_SeekBar);

            // android.widget.Space no tiene atributos ni derivadas
            // android.view.SurfaceView no tiene atributos
                // android.opengl.GLSurfaceView no tiene atributos ni derivadas
                // android.widget.VideoView no tiene atributos ni derivadas

            // android.support.design.widget.TabItem implementar???

            ClassDescView_widget_TextView widget_TextView = new ClassDescView_widget_TextView(this,view_View);
            addClassDesc(widget_TextView);
            init_widget_TextView_subClasses(widget_TextView);

            // 	android.view.TextureView no tiene atributos ni derivadas

            ClassDescView_view_ViewGroup view_ViewGroup = new ClassDescView_view_ViewGroup(this,view_View);
            addClassDesc(view_ViewGroup);
            init_view_ViewGroup_subClasses(view_ViewGroup);

            ClassDescView_view_ViewStub view_ViewStub = new ClassDescView_view_ViewStub(this,view_View);
            addClassDesc(view_ViewStub);
    }

    private void init_widget_TextView_subClasses(ClassDescView_widget_TextView widget_TextView)
    {
        ClassDescViewBased widget_Button = new ClassDescViewBased(this,"android.widget.Button",widget_TextView); // no tiene atributos
        addClassDesc(widget_Button);

        ClassDescView_widget_CompoundButton widget_CompoundButton = new ClassDescView_widget_CompoundButton(this,widget_Button);
        addClassDesc(widget_CompoundButton);

        // CheckBox no tiene atributos ni derivadas
        // RadioButton no tiene atributos ni derivadas y se testea indirectamente en RadioGroup

        ClassDescView_widget_Switch widget_Switch = new ClassDescView_widget_Switch(this,widget_CompoundButton);
        addClassDesc(widget_Switch);
        ClassDescView_widget_ToggleButton widget_ToggleButton = new ClassDescView_widget_ToggleButton(this,widget_CompoundButton);
        addClassDesc(widget_ToggleButton);

        ClassDescView_widget_CheckedTextView widget_CheckedTextView = new ClassDescView_widget_CheckedTextView(this,widget_TextView);
        addClassDesc(widget_CheckedTextView);

        ClassDescView_widget_Chronometer widget_Chronometer = new ClassDescView_widget_Chronometer(this,widget_TextView);
        addClassDesc(widget_Chronometer);

        // android.widget.DigitalClock no tiene atributos y está deprecated en level 17

        ClassDescViewBased widget_EditText = new ClassDescViewBased(this,"android.widget.EditText",widget_TextView); // no tiene atributos
        addClassDesc(widget_EditText);

        ClassDescView_widget_AutoCompleteTextView widget_AutoCompleteTextView = new ClassDescView_widget_AutoCompleteTextView(this,widget_EditText);
        addClassDesc(widget_AutoCompleteTextView);

        // android.widget.MultiAutoCompleteTextView no tiene atributos ni derivadas

        // android.inputmethodservice.ExtractEditText no tiene atributos ni derivadas

        // android.widget.TextClock es Level 17
    }

    private void init_view_ViewGroup_subClasses(ClassDescView_view_ViewGroup view_ViewGroup)
    {
        // AbsoluteLayout es deprecated en level 3 y su derivada (WebView) no tiene atributos

        ClassDescViewBased widget_AdapterView = new ClassDescViewBased(this,"android.widget.AdapterView",view_ViewGroup); // AdapterView no tiene atributos
        addClassDesc(widget_AdapterView);

            ClassDescView_widget_AbsListView widget_AbsListView = new ClassDescView_widget_AbsListView(this,widget_AdapterView);
            addClassDesc(widget_AbsListView);

                ClassDescView_widget_GridView widget_GridView = new ClassDescView_widget_GridView(this,widget_AbsListView);
                addClassDesc(widget_GridView);

                ClassDescView_widget_ListView widget_ListView = new ClassDescView_widget_ListView(this,widget_AbsListView);
                addClassDesc(widget_ListView);

                    ClassDescView_widget_ExpandableListView widget_ExListView = new ClassDescView_widget_ExpandableListView(this,widget_ListView);
                    addClassDesc(widget_ExListView);

            ClassDescView_widget_AbsSpinner widget_AbsSpinner = new ClassDescView_widget_AbsSpinner(this,widget_AdapterView);
            addClassDesc(widget_AbsSpinner);

                ClassDescView_widget_Gallery widget_Gallery = new ClassDescView_widget_Gallery(this,widget_AbsSpinner); // deprecated en level 16 pero como la tenemos hecha la conservamos
                addClassDesc(widget_Gallery);

                ClassDescView_widget_Spinner widget_Spinner = new ClassDescView_widget_Spinner(this,widget_AbsSpinner);
                addClassDesc(widget_Spinner);

            ClassDescView_widget_AdapterViewAnimator widget_AdapterViewAnimator = new ClassDescView_widget_AdapterViewAnimator(this,widget_AdapterView);
            addClassDesc(widget_AdapterViewAnimator);

                ClassDescView_widget_AdapterViewFlipper widget_AdapterViewFlipper = new ClassDescView_widget_AdapterViewFlipper(this,widget_AdapterViewAnimator);
                addClassDesc(widget_AdapterViewFlipper);

                // android.widget.StackView no tiene atributos ni derivadas


        // 	android.app.FragmentBreadCrumbs no tiene atributos ni derivadas y está deprecated en level 21

        ClassDescView_widget_FrameLayout widget_FrameLayout = new ClassDescView_widget_FrameLayout(this,view_ViewGroup);
        addClassDesc(widget_FrameLayout);

            ClassDescView_Include include = new ClassDescView_Include(this,widget_FrameLayout);
            addClassDesc(include);

            // android.appwidget.AppWidgetHostView no tiene atributos ni

            ClassDescView_widget_CalendarView widget_CalendarView = new ClassDescView_widget_CalendarView(this,widget_FrameLayout);
            addClassDesc(widget_CalendarView);

            ClassDescView_widget_DatePicker widget_DatePicker = new ClassDescView_widget_DatePicker(this,widget_FrameLayout);
            addClassDesc(widget_DatePicker);

            ClassDescView_gesture_GestureOverlayView widget_GestureOverlayView = new ClassDescView_gesture_GestureOverlayView(this,widget_FrameLayout);
            addClassDesc(widget_GestureOverlayView);

            ClassDescView_widget_HorizontalScrollView widget_HorizontalScrollView = new ClassDescView_widget_HorizontalScrollView(this,widget_FrameLayout);
            addClassDesc(widget_HorizontalScrollView);

            // android.widget.MediaController no tiene atributos

            ClassDescView_widget_ScrollView widget_ScrollView = new ClassDescView_widget_ScrollView(this,widget_FrameLayout);
            addClassDesc(widget_ScrollView);

            // android.widget.TabHost no tiene atributos
            // android.widget.TimePicker tiene un atributo android:timePickerMode pero es level 22

            ClassDescView_widget_ViewAnimator widget_ViewAnimator = new ClassDescView_widget_ViewAnimator(this,widget_FrameLayout);
            addClassDesc(widget_ViewAnimator);

                ClassDescView_widget_ViewFlipper widget_ViewFlipper = new ClassDescView_widget_ViewFlipper(this,widget_ViewAnimator);
                addClassDesc(widget_ViewFlipper);

                // android.widget.ViewSwitcher no tiene atributos
                    // android.widget.ImageSwitcher no tiene atributos
                    // android.widget.TextSwitcher no tiene atributos

        ClassDescView_widget_GridLayout widget_GridLayout = new ClassDescView_widget_GridLayout(this,view_ViewGroup);
        addClassDesc(widget_GridLayout);

        ClassDescView_widget_LinearLayout widget_LinearLayout = new ClassDescView_widget_LinearLayout(this,view_ViewGroup);
        addClassDesc(widget_LinearLayout);

            // android.widget.ActionMenuView es level 21
            // android.widget.NumberPicker no tiene atributos

            ClassDescView_widget_RadioGroup widget_RadioGroup = new ClassDescView_widget_RadioGroup(this,widget_LinearLayout);
            addClassDesc(widget_RadioGroup);

            ClassDescView_widget_SearchView widget_SearchView = new ClassDescView_widget_SearchView(this,widget_LinearLayout);
            addClassDesc(widget_SearchView);

            ClassDescView_widget_TabWidget widget_TabWidget = new ClassDescView_widget_TabWidget(this,widget_LinearLayout);
            addClassDesc(widget_TabWidget);

            ClassDescView_widget_TableLayout widget_TableLayout = new ClassDescView_widget_TableLayout(this,widget_LinearLayout);
            addClassDesc(widget_TableLayout);

            // android.widget.TableRow no tiene atributos ni derivadas
            // android.widget.ZoomControls no tiene atributos ni derivadas


        ClassDescView_widget_RelativeLayout widget_RelativeLayout = new ClassDescView_widget_RelativeLayout(this,view_ViewGroup);
        addClassDesc(widget_RelativeLayout);
            // android.widget.DialerFilter no tiene atributos
            // android.widget.TwoLineListItem tiene un atributo android:mode no implementado y la clase es deprecated en level 17

        ClassDescView_widget_SlidingDrawer widget_SlidingDrawer = new ClassDescView_widget_SlidingDrawer(this,view_ViewGroup); // This class was deprecated in API level 17
        addClassDesc(widget_SlidingDrawer);

        // android.widget.Toolbar es level 21
        // android.media.tv.TvView es level 21

    }


}
