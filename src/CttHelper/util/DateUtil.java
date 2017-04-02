package CttHelper.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * ��������������� ������� ��� ������ � ������.
 *
 * @author Marco Jakob
 */
public class DateUtil {

    /** ������ ����, ������������ ��� ��������������. ����� �������� �� ����. */
    private static final String DATE_PATTERN = "dd.MM.yyyy";

    /** ������������� ����. */
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * ���������� ���������� ���� � ���� ������ ����������������� ������.
     * ������������ ����������� ���� {@link DateUtil#DATE_PATTERN}.
     *
     * @param date - ����, ������� ����� ���������� � ���� ������
     * @return ����������������� ������
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * ����������� ������, ������� ��������������� �� ��������
     * ������� {@link DateUtil#DATE_PATTERN} � ������ {@link LocalDate}.
     *
     * ���������� null, ���� ������ �� ����� ���� �������������.
     *
     * @param dateString - ���� � ���� String
     * @return ������ ���� ��� null, ���� ������ �� ����� ���� �������������
     */
    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * ���������, �������� �� ������ ���������� �����.
     *
     * @param dateString
     * @return true, ���� ������ �������� ���������� �����
     */
    public static boolean validDate(String dateString) {
        // �������� ��������� ������.
        return DateUtil.parse(dateString) != null;
    }
}