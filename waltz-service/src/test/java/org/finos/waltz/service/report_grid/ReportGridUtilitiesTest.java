package org.finos.waltz.service.report_grid;

import org.jooq.lambda.tuple.Tuple2;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.finos.waltz.service.report_grid.ReportGridUtilities.parseTableData;
import static org.junit.jupiter.api.Assertions.*;

public class ReportGridUtilitiesTest {

    private final String TEST_STRING = "| Grid Name | Grid Identifier | Vantage Point Kind | Vantage Point Id |\n" +
            "| --- | --- | --- | --- |\n" +
            "| Grid Name | {GRIDEXTID} | ORG_UNIT | 1 |\n" +
            "\n" +
            "\n" +
            "| Filter Column | Column Option Codes |\n" +
            "| --- | --- |\n" +
            "| Asset Kind/Application | PROVIDED |\n" +
            "| Developer | PROVIDED |";

    private final String[] TEST_STRING_ARRAY = TEST_STRING.split("\\r?\\n");

    @Test
    public void emptyStringReturnsNull() {
        Object object = ReportGridUtilities.parseNoteText("");
        assertNull(object);
    }

    @Test
    public void nullStringReturnsNull() {
        Object object = ReportGridUtilities.parseNoteText(null);
        assertNull(object);
    }

    @Test
    public void parseEmptyTextShouldReturnEmptyList() {
        ArrayList<List<String>> cellList = parseTableData(null, "| Grid Name | Grid Identifier | Vantage Point Kind | Vantage Point Id |");
        assertEquals(0, cellList.size());
    }

    @Test
    public void parseNullTextShouldReturnEmptyList() {
        ArrayList<List<String>> cellList = parseTableData(null, "| Grid Name | Grid Identifier | Vantage Point Kind | Vantage Point Id |");
        assertEquals(0, cellList.size());
    }

    @Test
    public void parseNoteTextForHeaderShouldReturnOnlyOneRow() {
        ArrayList<List<String>> cellList = parseTableData(TEST_STRING_ARRAY, "| Grid Name | Grid Identifier | Vantage Point Kind | Vantage Point Id |");
        assertEquals(1, cellList.size());
    }

    @Test
    public void parseNoteTextForFiltersShouldReturnTwoRows() {
        ArrayList<List<String>> cellList = parseTableData(TEST_STRING_ARRAY, "| Filter Column | Column Option Codes |");
        assertEquals(2, cellList.size());
    }

    @Test
    public void parseNoteTextShouldReturnOneRowForGridInformation() {
        Tuple2<ArrayList<List<String>>, ArrayList<List<String>>> noteText = ReportGridUtilities.parseNoteText(TEST_STRING);
        assertEquals(1, noteText.v1.size(), "Should only return one header row");
    }

    @Test
    public void parseNoteTextShouldHaveFourFieldsInGridInfoRow() {
        Tuple2<ArrayList<List<String>>, ArrayList<List<String>>> noteText = ReportGridUtilities.parseNoteText(TEST_STRING);
        assertEquals(4, noteText.v1.get(0).size(), "Should have four fields of grid info in header row");
    }

    @Test
    public void parseNoteTextShouldHaveTwoFieldsInGridInfoRow() {
        Tuple2<ArrayList<List<String>>, ArrayList<List<String>>> noteText = ReportGridUtilities.parseNoteText(TEST_STRING);
        assertEquals(2, noteText.v2.get(0).size(), "Should have two fields in each filter info row");
    }

}
