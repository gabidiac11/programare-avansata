package pa.lab4.stablematching;

import java.util.Comparator;

/**
 * is used for the PreferencePrinter that prints preferences
 *
 * PreferencePrinter does:
 * - use a single function for printing preferences: Map<Student, Set<School>
 *                                                 Map<School, Set<Student>
 * - or stable matching results
 */
public interface PreferencePrintable  {
    String getName();
}
