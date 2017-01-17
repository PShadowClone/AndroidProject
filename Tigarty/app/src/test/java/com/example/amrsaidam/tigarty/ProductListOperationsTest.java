package com.example.amrsaidam.tigarty;

import com.com.tigarty.api.Entities.Product;
import com.com.tigarty.api.ProductListOperations;
import com.com.tigarty.api.TraderInvoiceOperations;
import com.example.amrsaidam.tigarty.Fragments.TraderInvoiceFragment;
import android.os.Environment;
import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.robolectric.RobolectricTestRunner;
//import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Amr Saidam on 12/13/2016.
 */
//@Config(sdk = 16, manifest = "src/main/AndroidManifest.xml")
//@//RunWith(RobolectricTestRunner.class)
public class ProductListOperationsTest{

    TraderInvoiceOperations traderInvoiceOperations;

    //@Rule

    @Before
    public void setUp()
    {
        traderInvoiceOperations = TraderInvoiceOperations.getInstance();
    }

    @Test
    public void testJsonConverter() throws Exception {


        String jsonContents = traderInvoiceOperations.JsonConverter("rize","827592845045");
        System.out.println(jsonContents);
        assertNotNull("JsonContent",jsonContents);

    }

}
