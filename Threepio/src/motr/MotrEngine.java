/*
 * File: MotrEngine.java
 * Project: moTR
 * Author: Jeff Houle
 */
package motr;

import java.io.File;
import threepio.engine.ThreepioEngine;
import threepio.tabler.container.ModelTable;

/**
 * MoTR is a small tool for converting a Threepio Model Table into a Motive Device XML file.
 * @author jhoule
 */
public class MotrEngine extends ThreepioEngine {

    /**
     * the string to place where moTR should insert data.
     */
    public static final String placeHolder = "<!-- moTR code -->";

    private static final String prefix = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "\n<!--" +
            "\n\tGenerated by moTR." +
            "\n-->" +
            "\n<deviceType xmlns=\"urn:dslforum-org:hdm-0-0\" xmlns:xsi=" +
            "\"http://www.w3.org/2001/XMLSchema-instance\" " +
            " xsi:schemaLocation=\"urn:dslforum-org:hdm-0-0 deviceType.xsd\">" +
            "\n\n\t<protocol>DEVICE_PROTOCOL_DSLFTR069v1</protocol>" +
            "\n\t<manufacturer>Unknown</manufacturer>" +
            "\n\t<manufacturerOUI>001122</manufacturerOUI>" +
            "\n\t<productClass>Unknown</productClass>" +
            "\n\t<modelName>Unknown</modelName>" +
            "\n" +
            "\n\t<dataModel>" +
            "\n\t\t<attributes>" +
            "\n\t\t\t<attribute>" +
            "\n\t\t\t\t<attributeName>notification</attributeName>" +
            "\n\t\t\t\t<attributeType>int</attributeType>" +
            "\n\t\t\t\t<minValue>0</minValue>" +
            "\n\t\t\t\t<maxValue>2</maxValue>" +
            "\n\t\t\t</attribute>" +
            "\n\t\t\t<attribute>" +
            "\n\t\t\t\t<attributeName>accessList</attributeName>" +
            "\n\t\t\t\t<attributeType>string</attributeType>" +
            "\n\t\t\t\t<array>true</array>" +
            "\n\t\t\t\t<attributeLength>64</attributeLength>" +
            "\n\t\t\t</attribute>" +
            "\n\t\t\t<attribute>" +
            "\n\t\t\t\t<attributeName>visibility</attributeName>" +
            "\n\t\t\t\t<attributeType>string</attributeType>" +
            "\n\t\t\t\t<attributeLength>64</attributeLength>" +
            "\n\t\t\t</attribute>" +
            "\n\t\t</attributes>" +
            "\n\t<parameters>" +
            "\n\t\t<parameter>" +
            "\n\t\t\t<parameterName>Device</parameterName>" +
            "\n\t\t\t<parameterType>object</parameterType>" +
            "\n\t\t\t<array>false</array>" +
            "\n";
    private static final String ending = "\n\t\t</parameter>" +
            "\n\t</parameters>" +
            "\n\t</dataModel>" +
            "\n<baselineConfiguration>" +
            "\n</baselineConfiguration>" +
            "\n</deviceType>" +
            "\n";
    
    /**
     * prints a model table as a Motive Device XML
     * @param table - the table to print
     * @param fileOut - the file to print out to.
     * @param diff - IGNORED
     * @param profiles - IGNORED
     * @param looks - IGNORED
     * @return true if the printing occurred successfully, false if not.
     * @throws Exception - when there is a problem printing.
     */
    @Override
    public boolean printModelTable(ModelTable table, File fileOut, boolean diff, boolean profiles, boolean looks) throws Exception
    {
        String info = convertTable(table, new MotrPrinter(), fileOut, prefix, ending, false, false);
        return (stringToFile(info, fileOut) != null);
    }

    /**
     *  Relies on the users's inserstion of <code>placeHolder</code> in a file.
     * Places the output Table in that place in the file, saving it to the path of fileOut.
     * The original file is not changed.
     * @param table - the table to insert.
     * @param fileOut - the file to output to.
     * @param wrap - the file that contains the information to wrap the table in.
     * @return true iff the file was made properly, false iff not.
     * @throws Exception - when wrapping or printing doesn't go well.
     * @see #placeHolder
     */
    public boolean printWrappedTable(ModelTable table, File fileOut, File wrap) throws Exception
    {
        String info = convertTable(table, new MotrPrinter(), fileOut, "", "", false, false);

        
        return (wrapStringWithFile(info, fileOut, wrap, placeHolder) != null);
    }

}
