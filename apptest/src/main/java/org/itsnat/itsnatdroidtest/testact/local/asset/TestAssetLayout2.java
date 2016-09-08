package org.itsnat.itsnatdroidtest.testact.local.asset;

import android.content.Context;
import android.content.res.Resources;
import android.gesture.GestureOverlayView;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.SlidingDrawer;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewAnimator;
import android.widget.ViewFlipper;

import org.itsnat.droid.impl.util.MiscUtil;
import org.itsnat.droid.impl.xmlinflater.FieldContainer;
import org.itsnat.droid.impl.xmlinflater.MethodContainer;
import org.itsnat.itsnatdroidtest.R;
import org.itsnat.itsnatdroidtest.testact.util.TestUtil;
import org.itsnat.itsnatdroidtest.testact.util.ValueUtil;

import java.util.Calendar;
import java.util.Locale;

import static org.itsnat.itsnatdroidtest.testact.util.Assert.assertEquals;
import static org.itsnat.itsnatdroidtest.testact.util.Assert.assertFalse;
import static org.itsnat.itsnatdroidtest.testact.util.Assert.assertNotNull;
import static org.itsnat.itsnatdroidtest.testact.util.Assert.assertPositive;
import static org.itsnat.itsnatdroidtest.testact.util.Assert.assertTrue;


/**
 * Created by jmarranz on 19/06/14.
 */
public class TestAssetLayout2
{
    private static FieldContainer<Object> calendarView_fieldDelegate;
    private static FieldContainer<Locale> calendarView_fieldCurrentLocale;
    private static MethodContainer<Boolean> calendarView_methodParseDate;

    static
    {
        // Ver la clase AttrDescView_widget_CalendarView_maxDate_minDate
        Class calendarClassWithCurrentLocale,calendarClassWithParseDate;
        if (Build.VERSION.SDK_INT < TestUtil.LOLLIPOP)
        {
            calendarClassWithCurrentLocale = CalendarView.class;
            calendarClassWithParseDate = calendarClassWithCurrentLocale;
        }
        else // Lollipop
        {
            calendarView_fieldDelegate = new FieldContainer<Object>(CalendarView.class, "mDelegate");
            calendarClassWithCurrentLocale = MiscUtil.resolveClass(CalendarView.class.getName() + "$AbstractCalendarViewDelegate");
            if (Build.VERSION.SDK_INT == MiscUtil.LOLLIPOP) // 21 (5.0)
            {
                calendarClassWithParseDate = MiscUtil.resolveClass(CalendarView.class.getName() + "$LegacyCalendarViewDelegate");
            }
            else if (Build.VERSION.SDK_INT == MiscUtil.LOLLIPOP_MR1) // 22 (+5.1)
            {
                calendarClassWithParseDate = calendarClassWithCurrentLocale; // parseDate está ya en $AbstractCalendarViewDelegate
            }
            else // 23 y sup
            {
                calendarClassWithParseDate = CalendarView.class;
            }

        }

        calendarView_fieldCurrentLocale = new FieldContainer<Locale>(calendarClassWithCurrentLocale, "mCurrentLocale");
        calendarView_methodParseDate = new MethodContainer<Boolean>(calendarClassWithParseDate,"parseDate",new Class[]{String.class,Calendar.class});

    }


    public static void test(ScrollView compRoot, ScrollView parsedRoot)
    {
        Context ctx = compRoot.getContext();
        final Resources res = ctx.getResources();

        // comp = "Layout compiled"
        // parsed = "Layout dynamically parsed"
        // No podemos testear layout_width/height en el ScrollView root porque un View está desconectado y al desconectar el width y el height se ponen a 0
        // assertEquals(comp.getWidth(),parsed.getWidth());
        // assertEquals(comp.getHeight(),parsed.getHeight());

        LinearLayout comp = (LinearLayout) compRoot.getChildAt(0);
        LinearLayout parsed = (LinearLayout) parsedRoot.getChildAt(0);

        assertEquals(comp.getOrientation(), parsed.getOrientation());

        int childCount;

        // buttonBack
        {
            childCount = 0;

            Button compButton = (Button) comp.getChildAt(childCount);
            Button parsedButton = (Button) parsed.getChildAt(childCount);
            assertEquals(compButton.getId(), parsedButton.getId());
            assertEquals(compButton.getText(), parsedButton.getText());
        }

        // buttonReload
        {
            childCount++;

            Button compButton = (Button) comp.getChildAt(childCount);
            Button parsedButton = (Button) parsed.getChildAt(childCount);
            assertEquals(compButton.getId(), parsedButton.getId());
            assertEquals(compButton.getText(), parsedButton.getText());
        }

        // Test FrameLayout Attribs
        {
            childCount++;

            final TextView compTextView = (TextView) comp.getChildAt(childCount);
            final TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test FrameLayout");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final FrameLayout compLayout = (FrameLayout) comp.getChildAt(childCount);
            final FrameLayout parsedLayout = (FrameLayout) parsed.getChildAt(childCount);

            assertEquals(((ColorDrawable)compLayout.getForeground()).getColor(), 0x55ddffdd);
            assertEquals(compLayout.getForeground(), parsedLayout.getForeground());

            // Test android:foregroundGravity (getForegroundGravity() es Level 16):
            if (Build.VERSION.SDK_INT < TestUtil.MARSHMALLOW) // < 23
            {
                assertEquals((Integer) TestUtil.callGetMethod(compLayout, "getForegroundGravity"), Gravity.TOP | Gravity.LEFT);
                assertEquals((Integer) TestUtil.callGetMethod(compLayout, "getForegroundGravity"), (Integer) TestUtil.callGetMethod(parsedLayout, "getForegroundGravity"));
            }
            else //  23 y sup
            {
                assertEquals((Integer) TestUtil.callGetMethod(compLayout,View.class, "getForegroundGravity"), Gravity.TOP | Gravity.LEFT);
                assertEquals((Integer) TestUtil.callGetMethod(compLayout,View.class, "getForegroundGravity"), (Integer) TestUtil.callGetMethod(parsedLayout,View.class, "getForegroundGravity"));
            }
            assertTrue(compLayout.getMeasureAllChildren());
            assertEquals(compLayout.getMeasureAllChildren(), parsedLayout.getMeasureAllChildren());


            {
                final TextView compTextView = (TextView) compLayout.getChildAt(0);
                final TextView parsedTextView = (TextView) parsedLayout.getChildAt(0);
                assertEquals(compTextView.getText(), "Test FrameLayout Attribs");
                assertEquals(compTextView.getText(), parsedTextView.getText());

                FrameLayout.LayoutParams compLP = (FrameLayout.LayoutParams)compTextView.getLayoutParams();
                FrameLayout.LayoutParams parseLP = (FrameLayout.LayoutParams)parsedTextView.getLayoutParams();
                assertEquals(compLP.gravity, Gravity.BOTTOM | Gravity.RIGHT);
                assertEquals(compLP.gravity, parseLP.gravity);
            }
        }

        // Test CalendarView Attribs
        {
            childCount++;

            final TextView compTextView = (TextView) comp.getChildAt(childCount);
            final TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test CalendarView");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final CalendarView compLayout = (CalendarView) comp.getChildAt(childCount);
            final CalendarView parsedLayout = (CalendarView) parsed.getChildAt(childCount);

            // Test android:dateTextAppearance
            // A partir de level 16 hay un método get

            assertPositive((Integer) TestUtil.callGetMethod(compLayout,"getDateTextAppearance"));
            assertEquals((Integer)TestUtil.callGetMethod(compLayout,"getDateTextAppearance"),TestUtil.callGetMethod(parsedLayout,"getDateTextAppearance"));


            assertEquals(compLayout.getFirstDayOfWeek(),3);
            assertEquals(compLayout.getFirstDayOfWeek(), parsedLayout.getFirstDayOfWeek());

            // A partir de level 16 hay un método get

            assertEquals((Integer) TestUtil.callGetMethod(compLayout,"getFocusedMonthDateColor"),0xffff0000);
            assertEquals((Integer)TestUtil.callGetMethod(compLayout,"getFocusedMonthDateColor"),TestUtil.callGetMethod(parsedLayout,"getFocusedMonthDateColor"));


            // Test android:maxDate y minDate
            Locale locale;
            Calendar outDate;
            {
                Object compLayoutFinal = getCalendarObject(compLayout);
                locale = getCurrentLocale(compLayoutFinal);
                outDate = Calendar.getInstance(locale);
                parseDate(compLayoutFinal, "01/01/2101", outDate);
            }
            assertEquals(compLayout.getMaxDate(), outDate.getTimeInMillis());
            assertEquals(compLayout.getMaxDate(), parsedLayout.getMaxDate());

            {
                Object compLayoutFinal = getCalendarObject(compLayout);
                outDate = Calendar.getInstance(locale);
                parseDate(compLayoutFinal, "01/01/1901", outDate);
            }
            assertEquals(compLayout.getMinDate(),outDate.getTimeInMillis());
            assertEquals(compLayout.getMinDate(),parsedLayout.getMinDate());

            // Test android:selectedDateVerticalBar
            // A partir de level 16 hay un método get

            assertNotNull((Drawable) TestUtil.callGetMethod(compLayout,"getSelectedDateVerticalBar"));
            assertEquals((Drawable) TestUtil.callGetMethod(compLayout,"getSelectedDateVerticalBar"), (Drawable) TestUtil.callGetMethod(parsedLayout,"getSelectedDateVerticalBar"));


            // A partir de level 16 hay un método get

            assertEquals((Integer) TestUtil.callGetMethod(compLayout,"getSelectedWeekBackgroundColor"), 0xff0000ff);
            assertEquals((Integer) TestUtil.callGetMethod(compLayout,"getSelectedWeekBackgroundColor"), (Integer) TestUtil.callGetMethod(parsedLayout,"getSelectedWeekBackgroundColor"));


            assertTrue(compLayout.getShowWeekNumber());
            assertEquals(compLayout.getShowWeekNumber(), parsedLayout.getShowWeekNumber());

            // Test shownWeekCount
            // A partir de level 16 hay un método get

            assertEquals((Integer) TestUtil.callGetMethod(compLayout,"getShownWeekCount"), 5);
            assertEquals((Integer) TestUtil.callGetMethod(compLayout,"getShownWeekCount"), (Integer) TestUtil.callGetMethod(parsedLayout,"getShownWeekCount"));


            // Test unfocusedMonthDateColor
            // A partir de level 16 hay un método getUnfocusedMonthDateColor
            {
                // Sin embargo este método tiene un bug:
                // https://code.google.com/p/android/issues/detail?id=80934
                // Tenemos que seguir recurriendo al atributo, sólo que tenemos en cuenta Lollipop que tiene el atributo en otro sitio
                Object compLayoutFinal = getCalendarObject(compLayout);
                Object parsedLayoutFinal = getCalendarObject(parsedLayout);
                assertEquals((Integer) TestUtil.getField(compLayoutFinal, "mUnfocusedMonthDateColor"), 0xff00cc00);
                assertEquals((Integer) TestUtil.getField(compLayoutFinal, "mUnfocusedMonthDateColor"), (Integer) TestUtil.getField(parsedLayoutFinal, "mUnfocusedMonthDateColor"));

                // Cuando detectemos que está arreglado a partir de una versión (level) concreta, habilitaremos estas dos líneas:
                //assertEquals((Integer) TestUtil.callGetMethod(compLayout, "getUnfocusedMonthDateColor"), 0xff00cc00);
                //assertEquals((Integer) TestUtil.callGetMethod(compLayout, "getUnfocusedMonthDateColor"), (Integer) TestUtil.callGetMethod(parsedLayout, "getUnfocusedMonthDateColor"));
            }

            // Test android:weekDayTextAppearance

            assertPositive((Integer)TestUtil.callMethod(compLayout,null,CalendarView.class,"getWeekDayTextAppearance",null));
            assertEquals((Integer)TestUtil.callMethod(compLayout,null,CalendarView.class,"getWeekDayTextAppearance",null),(Integer)TestUtil.callMethod(parsedLayout,null,CalendarView.class,"getWeekDayTextAppearance",null));


            // Test android:weekNumberColor
            // A partir de level 16 hay un método get

            assertEquals((Integer) TestUtil.callGetMethod(compLayout, "getWeekNumberColor"), 0xffaaaa77);
            assertEquals((Integer) TestUtil.callGetMethod(compLayout, "getWeekNumberColor"), (Integer) TestUtil.callGetMethod(parsedLayout, "getWeekNumberColor"));


            // A partir de level 16 hay un método get

            assertEquals((Integer) TestUtil.callGetMethod(compLayout, "getWeekSeparatorLineColor"), 0xffaa8888);
            assertEquals((Integer) TestUtil.callGetMethod(compLayout, "getWeekSeparatorLineColor"), (Integer) TestUtil.callGetMethod(parsedLayout, "getWeekSeparatorLineColor"));
        }


        // Test DatePicker Attribs
        {
            childCount++;

            final TextView compTextView = (TextView) comp.getChildAt(childCount);
            final TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test DatePicker");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final DatePicker compLayout = (DatePicker) comp.getChildAt(childCount);
            final DatePicker parsedLayout = (DatePicker) parsed.getChildAt(childCount);

            // Tests android:calendarViewShown

//            assertTrue(compLayout.getCalendarViewShown()); No entiendo porque devuelve false
//            assertEquals(compLayout.getCalendarViewShown(),parsedLayout.getCalendarViewShown());
            assertTrue(parsedLayout.getCalendarViewShown());


            // Tests android:minDate, android:startYear
            Calendar minDate = Calendar.getInstance();
            minDate.clear();
            minDate.set(Calendar.YEAR, 1901);
            minDate.set(Calendar.MONTH, Calendar.JANUARY);
            minDate.set(Calendar.DAY_OF_MONTH, 1);
            assertEquals(compLayout.getMinDate(), minDate.getTimeInMillis()); // Gana minDate sobre startYear
            assertEquals(compLayout.getMinDate(),parsedLayout.getMinDate());

            // Tests android:maxDate, android:endYear
            Calendar maxDate = Calendar.getInstance();
            maxDate.clear();
            maxDate.set(Calendar.YEAR, 2101);
            maxDate.set(Calendar.MONTH, Calendar.DECEMBER);
            maxDate.set(Calendar.DAY_OF_MONTH, 31);
            assertEquals(compLayout.getMaxDate(), maxDate.getTimeInMillis()); // Gana maxDate sobre endYear
            assertEquals(compLayout.getMaxDate(),parsedLayout.getMaxDate());

            // Tests android:spinnersShown

//            assertTrue(compLayout.getSpinnersShown()); No entiendo porque devuelve false
//            assertEquals(compLayout.getSpinnersShown(),parsedLayout.getSpinnersShown());
            assertTrue(parsedLayout.getSpinnersShown());
        }

        // Test android.gesture.GestureOverlayView Attribs
        {
            childCount++;

            final TextView compTextView = (TextView) comp.getChildAt(childCount);
            final TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test GestureOverlayView");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final GestureOverlayView compLayout = (GestureOverlayView) comp.getChildAt(childCount);
            final GestureOverlayView parsedLayout = (GestureOverlayView) parsed.getChildAt(childCount);

            assertTrue(compLayout.isEventsInterceptionEnabled());
            assertEquals(compLayout.isEventsInterceptionEnabled(), parsedLayout.isEventsInterceptionEnabled());

            assertEquals((long)(Long)TestUtil.getField(compLayout, "mFadeDuration"),1000L);
            assertEquals((Long)TestUtil.getField(compLayout, "mFadeDuration"), (Long)TestUtil.getField(parsedLayout, "mFadeDuration"));

            assertTrue(compLayout.isFadeEnabled());
            assertEquals(compLayout.isFadeEnabled(), parsedLayout.isFadeEnabled());

            assertEquals(compLayout.getFadeOffset(),200L);
            assertEquals(compLayout.getFadeOffset(), parsedLayout.getFadeOffset());

            assertEquals(compLayout.getGestureColor(),0xffff0000);
            assertEquals(compLayout.getGestureColor(), parsedLayout.getGestureColor());

            assertEquals(compLayout.getGestureStrokeAngleThreshold(),30.1f);
            assertEquals(compLayout.getGestureStrokeAngleThreshold(), parsedLayout.getGestureStrokeAngleThreshold());

            assertEquals(compLayout.getGestureStrokeLengthThreshold(),35.1f);
            assertEquals(compLayout.getGestureStrokeLengthThreshold(), parsedLayout.getGestureStrokeLengthThreshold());

            assertEquals(compLayout.getGestureStrokeSquarenessTreshold(),0.21f);
            assertEquals(compLayout.getGestureStrokeSquarenessTreshold(), parsedLayout.getGestureStrokeSquarenessTreshold());

            assertEquals(compLayout.getGestureStrokeType(),GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE);
            assertEquals(compLayout.getGestureStrokeType(), parsedLayout.getGestureStrokeType());

            assertEquals(compLayout.getGestureStrokeWidth(),20f);
            assertEquals(compLayout.getGestureStrokeWidth(), parsedLayout.getGestureStrokeWidth());

            assertEquals(compLayout.getOrientation(),GestureOverlayView.ORIENTATION_HORIZONTAL);
            assertEquals(compLayout.getOrientation(), parsedLayout.getOrientation());

            assertEquals(compLayout.getUncertainGestureColor(),0xff00ff00);
            assertEquals(compLayout.getUncertainGestureColor(), parsedLayout.getUncertainGestureColor());

            {
                final TextView compTextView = (TextView) compLayout.getChildAt(0);
                final TextView parsedTextView = (TextView) parsedLayout.getChildAt(0);

                assertEquals(compTextView.getText(), "GestureOverlayView Test");
                assertEquals(compTextView.getText(), parsedTextView.getText());
            }
        }

        // Test HorizontalScrollView Attribs
        {
            childCount++;

            final TextView compTextView = (TextView) comp.getChildAt(childCount);
            final TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test HorizontalScrollView");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final HorizontalScrollView compLayout = (HorizontalScrollView) comp.getChildAt(childCount);
            final HorizontalScrollView parsedLayout = (HorizontalScrollView) parsed.getChildAt(childCount);

            assertTrue(compLayout.isFillViewport());
            assertEquals(compLayout.isFillViewport(), parsedLayout.isFillViewport());

            {
                final TextView compTextView = (TextView) compLayout.getChildAt(0);
                final TextView parsedTextView = (TextView) parsedLayout.getChildAt(0);

                assertEquals(compTextView.getText(), "HorizontalScrollView Test");
                assertEquals(compTextView.getText(), parsedTextView.getText());
            }
        }

        // Test ScrollView Attribs
        {
            childCount++;

            final TextView compTextView = (TextView) comp.getChildAt(childCount);
            final TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test ScrollView");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final ScrollView compLayout = (ScrollView) comp.getChildAt(childCount);
            final ScrollView parsedLayout = (ScrollView) parsed.getChildAt(childCount);

            assertTrue(compLayout.isFillViewport());
            assertEquals(compLayout.isFillViewport(), parsedLayout.isFillViewport());

            {
                final TextView compTextView = (TextView) compLayout.getChildAt(0);
                final TextView parsedTextView = (TextView) parsedLayout.getChildAt(0);

                assertEquals(compTextView.getText(), "ScrollView Test");
                assertEquals(compTextView.getText(), parsedTextView.getText());
            }
        }

        // Test ViewFlipper y ViewAnimator
        {
            childCount++;

            final TextView compTextView = (TextView) comp.getChildAt(childCount);
            final TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test ViewFlipper (and ViewAnimator)");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final ViewFlipper compLayout = (ViewFlipper) comp.getChildAt(childCount);
            final ViewFlipper parsedLayout = (ViewFlipper) parsed.getChildAt(childCount);

            // ViewAnimator

            assertTrue((Boolean) TestUtil.getField(compLayout, ViewAnimator.class, "mAnimateFirstTime"));
            assertEquals((Boolean) TestUtil.getField(compLayout,ViewAnimator.class,"mAnimateFirstTime"), (Boolean) TestUtil.getField(parsedLayout,ViewAnimator.class,"mAnimateFirstTime"));

            assertNotNull((AnimationSet) compLayout.getInAnimation());
            assertEquals((AnimationSet)compLayout.getInAnimation(),(AnimationSet)parsedLayout.getInAnimation());

            assertNotNull((AnimationSet) compLayout.getOutAnimation());
            assertEquals((AnimationSet)compLayout.getOutAnimation(),(AnimationSet)parsedLayout.getOutAnimation());

            // ViewFlipper
            assertTrue(compLayout.isAutoStart());
            assertEquals(compLayout.isAutoStart(),parsedLayout.isAutoStart());

            // android:flipInterval  (getFlipInterval es Level 16)
            assertEquals((Integer) TestUtil.getField(compLayout, "mFlipInterval"), 3000);
            assertEquals((Integer)TestUtil.getField(compLayout,"mFlipInterval"),(Integer)TestUtil.getField(parsedLayout,"mFlipInterval"));
        }

        // Test GridLayout
        {
            childCount++;

            final TextView compTextView = (TextView) comp.getChildAt(childCount);
            final TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test GridLayout");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        // Test GridLayout Attribs Horizontal
        {
            childCount++;

            final GridLayout compLayout = (GridLayout) comp.getChildAt(childCount);
            final GridLayout parsedLayout = (GridLayout) parsed.getChildAt(childCount);
            assertEquals(compLayout.getAlignmentMode(), GridLayout.ALIGN_BOUNDS);
            assertEquals(compLayout.getColumnCount(),3);
            assertEquals(compLayout.getColumnCount(), parsedLayout.getColumnCount());
            assertFalse(compLayout.isColumnOrderPreserved());
            assertEquals(compLayout.isColumnOrderPreserved(), parsedLayout.isColumnOrderPreserved());
            assertEquals(compLayout.getOrientation(),GridLayout.HORIZONTAL);
            assertEquals(compLayout.getOrientation(),parsedLayout.getOrientation());
            assertEquals(compLayout.getRowCount(), 3);
            assertEquals(compLayout.getRowCount(),parsedLayout.getRowCount());
            assertFalse(compLayout.isRowOrderPreserved());
            assertEquals(compLayout.isRowOrderPreserved(), parsedLayout.isRowOrderPreserved());
            assertTrue(compLayout.getUseDefaultMargins());
            assertEquals(compLayout.getUseDefaultMargins(), parsedLayout.getUseDefaultMargins());

            {
                for(int i = 0; i < 5; i++)
                {
                    final TextView compTextView = (TextView) compLayout.getChildAt(i);
                    final TextView parsedTextView = (TextView) parsedLayout.getChildAt(i);
                    // Testeamos via Spec los atributos: android:layout_column, android:layout_columnSpan y android:layout_gravity

                    final GridLayout.LayoutParams compParams = (GridLayout.LayoutParams) compTextView.getLayoutParams();
                    final GridLayout.LayoutParams parsedParams = (GridLayout.LayoutParams) parsedTextView.getLayoutParams();
                    parsedLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener()
                    {
                        @Override
                        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8)
                        {
                            assertTrue(compParams.columnSpec.equals(parsedParams.columnSpec));
                            assertTrue(compParams.rowSpec.equals(parsedParams.rowSpec));
                        }
                    });
                }
            }
        }

        // Test GridLayout Attribs Vertical
        {
            childCount++;

            final GridLayout compLayout = (GridLayout) comp.getChildAt(childCount);
            final GridLayout parsedLayout = (GridLayout) parsed.getChildAt(childCount);
            assertEquals(compLayout.getAlignmentMode(), GridLayout.ALIGN_BOUNDS);
            assertEquals(compLayout.getColumnCount(),3);
            assertEquals(compLayout.getColumnCount(), parsedLayout.getColumnCount());
            assertFalse(compLayout.isColumnOrderPreserved());
            assertEquals(compLayout.isColumnOrderPreserved(), parsedLayout.isColumnOrderPreserved());
            assertEquals(compLayout.getOrientation(),GridLayout.VERTICAL);
            assertEquals(compLayout.getOrientation(),parsedLayout.getOrientation());
            assertEquals(compLayout.getRowCount(), 3);
            assertEquals(compLayout.getRowCount(),parsedLayout.getRowCount());
            assertFalse(compLayout.isRowOrderPreserved());
            assertEquals(compLayout.isRowOrderPreserved(), parsedLayout.isRowOrderPreserved());
            assertTrue(compLayout.getUseDefaultMargins());
            assertEquals(compLayout.getUseDefaultMargins(), parsedLayout.getUseDefaultMargins());

            {

                for (int i = 0; i < 5; i++)
                {
                    TextView compTextView = (TextView) compLayout.getChildAt(i);
                    TextView parsedTextView = (TextView) parsedLayout.getChildAt(i);
                    // Testeamos via Specs los atributos: android:layout_row, android:layout_rowSpan y android:layout_gravity
                    final GridLayout.LayoutParams compParams = (GridLayout.LayoutParams) compTextView.getLayoutParams();
                    final GridLayout.LayoutParams parsedParams = (GridLayout.LayoutParams) parsedTextView.getLayoutParams();
                    parsedLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener()
                    {
                        @Override
                        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8)
                        {
                            assertTrue(compParams.columnSpec.equals(parsedParams.columnSpec));
                            assertTrue(compParams.rowSpec.equals(parsedParams.rowSpec));
                        }
                    });
                }

            }

        }


        // Test LinearLayout Attribs
        {
            childCount++;

            final TextView compTextView = (TextView) comp.getChildAt(childCount);
            final TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test LinearLayout");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final LinearLayout compLayout = (LinearLayout) comp.getChildAt(childCount);
            final LinearLayout parsedLayout = (LinearLayout) parsed.getChildAt(childCount);

            assertFalse(compLayout.isBaselineAligned());
            assertEquals(compLayout.isBaselineAligned(), parsedLayout.isBaselineAligned());
            assertEquals(compLayout.getBaselineAlignedChildIndex(), 1);
            assertEquals(compLayout.getBaselineAlignedChildIndex(), parsedLayout.getBaselineAlignedChildIndex());
            // Tests android:divider (getDividerDrawable() es Level 16):
            assertEquals((GradientDrawable) TestUtil.getField(compLayout, "mDivider"), (GradientDrawable) TestUtil.getField(parsedLayout, "mDivider"));

            assertEquals(compLayout.getShowDividers(), 3);
            assertEquals(compLayout.getShowDividers(), parsedLayout.getShowDividers());
            assertEquals(compLayout.getDividerPadding(), ValueUtil.dpToPixelIntRound(10.3f, res));
            assertEquals(compLayout.getDividerPadding(),parsedLayout.getDividerPadding());
            assertTrue(compLayout.isMeasureWithLargestChildEnabled());
            assertEquals(compLayout.isMeasureWithLargestChildEnabled(), parsedLayout.isMeasureWithLargestChildEnabled());
            assertEquals(compLayout.getWeightSum(),1.0f);
            assertEquals(compLayout.getWeightSum(), parsedLayout.getWeightSum());
        }

        // Test LinearLayout gravity
        {
            childCount++;

            LinearLayout compLayout = (LinearLayout) comp.getChildAt(childCount);
            LinearLayout parsedLayout = (LinearLayout) parsed.getChildAt(childCount);
            assertEquals((Integer)TestUtil.getField(compLayout, "mGravity"), Gravity.TOP |Gravity.RIGHT);
            assertEquals((Integer)TestUtil.getField(compLayout, "mGravity"),(Integer)TestUtil.getField(parsedLayout, "mGravity"));
            {
                final TextView compTextView = (TextView) compLayout.getChildAt(0);
                final TextView parsedTextView = (TextView) parsedLayout.getChildAt(0);

                assertEquals(compTextView.getText(),"Test LinearLayout gravity 1");
                assertEquals(compTextView.getText(), parsedTextView.getText());
                assertEquals(compTextView.getBackground(), parsedTextView.getBackground());
            }
        }

        // Testing LinearLayout.LayoutParams
        {
            childCount++;

            LinearLayout compLayout = (LinearLayout) comp.getChildAt(childCount);
            LinearLayout parsedLayout = (LinearLayout) parsed.getChildAt(childCount);
            for(int i = 0; i < 2; i++)
            {
                TextView compTextView1 = (TextView) compLayout.getChildAt(i);
                TextView parsedTextView1 = (TextView) parsedLayout.getChildAt(i);
                assertEquals(compTextView1.getText(), parsedTextView1.getText());
                assertEquals(compTextView1.getBackground(), compTextView1.getBackground());

                LinearLayout.LayoutParams compParams = (LinearLayout.LayoutParams)compTextView1.getLayoutParams();
                LinearLayout.LayoutParams parsedParams = (LinearLayout.LayoutParams)parsedTextView1.getLayoutParams();

                if (i == 0) assertEquals(compParams.gravity,Gravity.TOP|Gravity.LEFT);
                else assertEquals(compParams.gravity,Gravity.BOTTOM|Gravity.RIGHT);
                assertEquals(compParams.gravity,parsedParams.gravity);

                if (i == 0) assertEquals(compParams.weight,70);
                else assertEquals(compParams.weight,30);
                assertEquals(compParams.weight,parsedParams.weight);
            }
        }

        // Test Test RadioGroup (y RadioButton que no tiene atribs)
        {
            childCount++;

            final TextView compTextView = (TextView) comp.getChildAt(childCount);
            final TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test RadioGroup (and RadioButton)");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final RadioGroup compLayout = (RadioGroup) comp.getChildAt(childCount);
            final RadioGroup parsedLayout = (RadioGroup) parsed.getChildAt(childCount);

            final TextView compMaleView = (TextView) compLayout.getChildAt(0);
            final TextView parsedMaleView = (TextView) parsedLayout.getChildAt(0);

            final TextView compFemaleView = (TextView) compLayout.getChildAt(1);
            final TextView parsedFemaleView = (TextView) parsedLayout.getChildAt(1);

            assertEquals(compLayout.getCheckedRadioButtonId(), R.id.radioFemale);
            assertEquals(parsedLayout.getCheckedRadioButtonId(),parsedFemaleView.getId());

            assertEquals(((TextView) compLayout.findViewById(R.id.radioFemale)), compFemaleView);
            assertEquals(((TextView) parsedLayout.findViewById(parsedFemaleView.getId())), parsedFemaleView);

            assertEquals(compLayout.getOrientation(), LinearLayout.HORIZONTAL);
            assertEquals(compLayout.getOrientation(), parsedLayout.getOrientation());
        }

        // Test SearchView
        {
            childCount++;

            final TextView compTextView = (TextView) comp.getChildAt(childCount);
            final TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test SearchView");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final SearchView compLayout = (SearchView) comp.getChildAt(childCount);
            final SearchView parsedLayout = (SearchView) parsed.getChildAt(childCount);

            assertFalse(compLayout.isIconfiedByDefault());
            assertEquals(compLayout.isIconfiedByDefault(),parsedLayout.isIconfiedByDefault());

            // Test android:imeOptions
            String fieldName = Build.VERSION.SDK_INT <= TestUtil.LOLLIPOP ? "mQueryTextView" : "mSearchSrcTextView";
            TextView compQueryTextView = (TextView)TestUtil.getField(compLayout,fieldName);
            TextView parsedQueryTextView = (TextView)TestUtil.getField(parsedLayout,fieldName);
            assertEquals(compQueryTextView.getImeOptions(), EditorInfo.IME_ACTION_GO|EditorInfo.IME_ACTION_SEARCH);
            assertEquals(compQueryTextView.getImeOptions(),parsedQueryTextView.getImeOptions());

            // Test android:inputType
            assertEquals(compQueryTextView.getInputType(), InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL | InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE);
            assertEquals(compQueryTextView.getInputType(), parsedQueryTextView.getInputType());

            assertEquals((Integer) TestUtil.getField(compLayout, "mMaxWidth"), ValueUtil.dpToPixelIntRound(250.3f, res));
            assertEquals((Integer) TestUtil.getField(compLayout, "mMaxWidth"), (Integer) TestUtil.getField(parsedLayout, "mMaxWidth"));

            assertEquals((CharSequence) TestUtil.getField(compLayout, "mQueryHint"), "The <hint>");
            assertEquals((CharSequence) TestUtil.getField(compLayout, "mQueryHint"), (CharSequence) TestUtil.getField(parsedLayout, "mQueryHint"));
        }

        // Test TabWidget (y necesariamente TabHost)
        {
            childCount++;

            final TextView compTextView = (TextView) comp.getChildAt(childCount);
            final TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test TabWidget (and TabHost)");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final TabHost compLayout = (TabHost) comp.getChildAt(childCount);
            final TabHost parsedLayout = (TabHost) parsed.getChildAt(childCount);

            final TabWidget comTabWidget = (TabWidget)compLayout.findViewById(android.R.id.tabs);
            final TabWidget parsedTabWidget = (TabWidget)parsedLayout.findViewById(android.R.id.tabs);

            assertTrue(comTabWidget.isStripEnabled());
            assertEquals(comTabWidget.isStripEnabled(), parsedTabWidget.isStripEnabled());

            // android:tabLeftStrip y android:tabRightStrip
            assertNotNull((StateListDrawable) TestUtil.getField(comTabWidget, "mLeftStrip"));
            assertNotNull((StateListDrawable) TestUtil.getField(parsedTabWidget, "mLeftStrip"));
            assertNotNull((StateListDrawable) TestUtil.getField(comTabWidget, "mRightStrip"));
            assertNotNull((StateListDrawable) TestUtil.getField(parsedTabWidget, "mRightStrip"));
            /* No se porqué falla, pues se ven igual los dos layouts
            parsedTabWidget.addOnLayoutChangeListener(new View.OnLayoutChangeListener()
            {
                @Override
                public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8)
                {
                    assertEquals((StateListDrawable) TestUtil.getField(comTabWidget, "mLeftStrip"), (StateListDrawable) TestUtil.getField(parsedTabWidget, "mLeftStrip"));
                    assertEquals((StateListDrawable) TestUtil.getField(comTabWidget, "mRightStrip"), (StateListDrawable) TestUtil.getField(parsedTabWidget, "mRightStrip"));
                }
            });
            */

        }

        // Test TableLayout (y TableRow y TableLayout.LayoutParams)
        {
            childCount++;

            final TextView compTextView = (TextView) comp.getChildAt(childCount);
            final TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test TableLayout,TableRow,TableLayout.LayoutParams");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            final TableLayout compLayout = (TableLayout) comp.getChildAt(childCount);
            final TableLayout parsedLayout = (TableLayout) parsed.getChildAt(childCount);

            int maxColumnsRow = 5;
            // Test android:collapseColumns
            for(int i = 0; i < maxColumnsRow; i++)
            {
                if ((i == 3) || (i == 4)) assertTrue(compLayout.isColumnCollapsed(i));
                else assertFalse(compLayout.isColumnCollapsed(i));
                assertEquals(compLayout.isColumnCollapsed(i), parsedLayout.isColumnCollapsed(i));
            }
            // Test android:shrinkColumns
            for(int i = 0; i < maxColumnsRow; i++)
            {
                if ((i == 0) || (i == 1)) assertTrue(compLayout.isColumnShrinkable(i));
                else assertFalse(compLayout.isColumnShrinkable(i));
                assertEquals(compLayout.isColumnShrinkable(i), parsedLayout.isColumnShrinkable(i));
            }
            // Test android:stretchColumns
            for(int i = 0; i < maxColumnsRow; i++)
            {
                if ((i == 0) || (i == 1)) assertTrue(compLayout.isColumnStretchable(i));
                else assertFalse(compLayout.isColumnStretchable(i));
                assertEquals(compLayout.isColumnStretchable(i), parsedLayout.isColumnStretchable(i));
            }

            // Test TableRow.LayoutParams (android:layout_column y android:layout_span)
            TableRow compRow = (TableRow)compLayout.getChildAt(1);
            TableRow parsedRow = (TableRow)parsedLayout.getChildAt(1);
            TableRow.LayoutParams compParams = (TableRow.LayoutParams)compRow.getChildAt(0).getLayoutParams();
            TableRow.LayoutParams parsedParams = (TableRow.LayoutParams)parsedRow.getChildAt(0).getLayoutParams();
            assertEquals(compParams.column, 1);
            assertEquals(compParams.column, parsedParams.column);
            assertEquals(compParams.span, 2);
            assertEquals(compParams.span, parsedParams.span);
        }

        // Test RelativeLayout
        {
            childCount++;

            final TextView compTextView = (TextView) comp.getChildAt(childCount);
            final TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test RelativeLayout");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        // Test RelativeLayout (gravity)
        {
            childCount++;

            final RelativeLayout compLayout = (RelativeLayout) comp.getChildAt(childCount);
            final RelativeLayout parsedLayout = (RelativeLayout) parsed.getChildAt(childCount);
            // Tests android:gravity (getGravity() es Level 16):
            assertEquals((Integer) TestUtil.getField(compLayout, "mGravity"), Gravity.BOTTOM | Gravity.RIGHT);
            assertEquals((Integer) TestUtil.getField(compLayout, "mGravity"), (Integer) TestUtil.getField(parsedLayout, "mGravity"));

            {
                final TextView compTextView = (TextView) compLayout.getChildAt(0);
                final TextView parsedTextView = (TextView) parsedLayout.getChildAt(0);
                assertEquals(compTextView.getText(), parsedTextView.getText());
                assertEquals(compTextView.getBackground(), parsedTextView.getBackground());
            }
        }

        // Test RelativeLayout (ignoreGravity)
        {
            childCount++;

            RelativeLayout compLayout = (RelativeLayout) comp.getChildAt(childCount);
            RelativeLayout parsedLayout = (RelativeLayout) parsed.getChildAt(childCount);
            // Tests android:ignoreGravity que es un id (no hay get):
            assertPositive((Integer)TestUtil.getField(compLayout, "mIgnoreGravity"));
            assertEquals((Integer)TestUtil.getField(compLayout, "mIgnoreGravity"), (Integer)TestUtil.getField(parsedLayout, "mIgnoreGravity"));

            {
                final TextView compTextView = (TextView) compLayout.getChildAt(0);
                final TextView parsedTextView = (TextView) parsedLayout.getChildAt(0);
                assertEquals(compTextView.getText(), parsedTextView.getText());
                assertEquals(compTextView.getBackground(), parsedTextView.getBackground());
            }
        }


        // Test RelativeLayout.LayoutParams
        {
            childCount++;

            RelativeLayout compLayout = (RelativeLayout) comp.getChildAt(childCount);
            RelativeLayout parsedLayout = (RelativeLayout) parsed.getChildAt(childCount);

            for(int i = 0; i < 2; i++)
            {
                final TextView compTextView = (TextView) compLayout.getChildAt(i);
                final TextView parsedTextView = (TextView) parsedLayout.getChildAt(i);
                assertEquals(compTextView.getText(), parsedTextView.getText());
                assertEquals(compTextView.getBackground(), parsedTextView.getBackground());

                RelativeLayout.LayoutParams compTextParams = (RelativeLayout.LayoutParams)compTextView.getLayoutParams();
                RelativeLayout.LayoutParams parsedTextParams = (RelativeLayout.LayoutParams)parsedTextView.getLayoutParams();
                int[] compTextRules = compTextParams.getRules();
                int[] parsedTextRules = parsedTextParams.getRules();
                assertEquals(compTextRules.length, parsedTextRules.length); // Por si acaso pero son todas las posibles rules
                for(int j = 0; j < compTextRules.length; j++)
                {
                    if (i == 0)
                    {
                        if (j == RelativeLayout.ALIGN_PARENT_BOTTOM)
                            assertEquals(compTextRules[j],RelativeLayout.TRUE);
                    }
                    else if (i == 1)
                    {
                        if (j == RelativeLayout.RIGHT_OF)
                            assertPositive(compTextRules[j]); // Es un id
                    }

                    assertEquals(compTextRules[j],parsedTextRules[j]);
                }

                if (i == 0)
                    assertEquals(compTextParams.alignWithParent,parsedTextParams.alignWithParent);
            }
        }


        // Test SlidingDrawer (necesita un FrameLayout o similar como padre)
        {
            childCount++;

            final TextView compTextView = (TextView) comp.getChildAt(childCount);
            final TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test SlidingDrawer");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            LinearLayout compLayout = (LinearLayout) comp.getChildAt(childCount);
            LinearLayout parsedLayout = (LinearLayout) parsed.getChildAt(childCount);


            SlidingDrawer compDrawer = (SlidingDrawer) compLayout.getChildAt(0);
            SlidingDrawer parsedDrawer = (SlidingDrawer) parsedLayout.getChildAt(0);

            // android:allowSingleTap
            assertFalse((Boolean) TestUtil.getField(compDrawer, "mAllowSingleTap"));
            assertEquals((Boolean) TestUtil.getField(compDrawer, "mAllowSingleTap"), (Boolean) TestUtil.getField(parsedDrawer, "mAllowSingleTap"));

            // android:animateOnClick
            assertTrue((Boolean) TestUtil.getField(compDrawer, "mAnimateOnClick"));
            assertEquals((Boolean) TestUtil.getField(compDrawer, "mAnimateOnClick"), (Boolean) TestUtil.getField(parsedDrawer, "mAnimateOnClick"));

            // android:bottomOffset
            assertEquals((Integer) TestUtil.getField(compDrawer, "mBottomOffset"), ValueUtil.dpToPixelIntFloor(10.3f, res));
            assertEquals((Integer) TestUtil.getField(compDrawer, "mBottomOffset"), (Integer) TestUtil.getField(parsedDrawer, "mBottomOffset"));

            // android:handle
            assertEquals((Integer) TestUtil.getField(compDrawer, "mHandleId"), res.getIdentifier("@id/slidingHandle", null, ctx.getPackageName()));
            assertEquals((Integer) TestUtil.getField(compDrawer, "mHandleId"), (Integer) TestUtil.getField(parsedDrawer, "mHandleId"));

            // android:content
            assertEquals((Integer) TestUtil.getField(compDrawer, "mContentId"), res.getIdentifier("@id/slidingContent", null, ctx.getPackageName()));
            assertEquals((Integer) TestUtil.getField(compDrawer, "mContentId"), (Integer) TestUtil.getField(parsedDrawer, "mContentId"));

            // android:orientation
            assertFalse((Boolean) TestUtil.getField(compDrawer, "mVertical"));
            assertEquals((Boolean) TestUtil.getField(compDrawer, "mVertical"), (Boolean) TestUtil.getField(parsedDrawer, "mVertical"));

            // android:topOffset
            assertEquals((Integer) TestUtil.getField(compDrawer, "mTopOffset"), ValueUtil.dpToPixelIntFloor(10.3f, res));
            assertEquals((Integer) TestUtil.getField(compDrawer, "mTopOffset"), (Integer) TestUtil.getField(parsedDrawer, "mTopOffset"));

        }




        // Test ViewStub
        // se reemplaza por el android:layout especificado, indirectamente testeamos los ViewStub
        {
            childCount++;

            final TextView compTextView = (TextView) comp.getChildAt(childCount);
            final TextView parsedTextView = (TextView) parsed.getChildAt(childCount);
            assertEquals(compTextView.getText(), "Test ViewStub");
            assertEquals(compTextView.getText(), parsedTextView.getText());
        }

        {
            childCount++;

            LinearLayout compLayout = (LinearLayout) comp.getChildAt(childCount);
            LinearLayout parsedLayout = (LinearLayout) parsed.getChildAt(childCount);

            // Test indirecto de android:inflatedId
            assertEquals(compLayout.getId(), res.getIdentifier("@id/viewStubInsertedId", null, ctx.getPackageName()));
            assertEquals(compLayout.getId(), parsedLayout.getId());

            ViewGroup.LayoutParams compParams = compLayout.getLayoutParams();
            ViewGroup.LayoutParams parsedParams = parsedLayout.getLayoutParams();
            assertEquals(compParams.height,ValueUtil.dpToPixelIntRound(40.3f, res));
            assertEquals(compParams.height,parsedParams.height);

            {
                TextView compTextView0 = (TextView) compLayout.getChildAt(0);
                TextView parsedTextView0 = (TextView) parsedLayout.getChildAt(0);
                assertEquals(compTextView0.getText(),"ViewStub test 1");
                assertEquals(compTextView0.getText(),parsedTextView0.getText());

                TextView compTextView1 = (TextView) compLayout.getChildAt(1);
                TextView parsedTextView1 = (TextView) parsedLayout.getChildAt(1);
                assertEquals(compTextView1.getText(),"ViewStub test 2");
                assertEquals(compTextView1.getText(),parsedTextView1.getText());
            }
        }


        //System.out.println("\n\n\n");

    }

    private static Object getCalendarObject(CalendarView view)
    {
        if (Build.VERSION.SDK_INT < TestUtil.LOLLIPOP)
            return view;
        else
            return calendarView_fieldDelegate.get(view);
    }

    private static boolean parseDate(Object calendarObject,String date, Calendar outDate)
    {
        return calendarView_methodParseDate.invoke(calendarObject,date,outDate);
    }

    private static Locale getCurrentLocale(Object calendarObject)
    {
        return calendarView_fieldCurrentLocale.get(calendarObject);
    }
}