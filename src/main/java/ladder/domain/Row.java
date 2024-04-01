package ladder.domain;

import ladder.rowgenerator.RowGenerator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Row implements Iterable<Boolean> {
  private final List<Boolean> values;

  public static Row of(final int[] row) {
    return new Row(Arrays.stream(row)
            .mapToObj(Row::convertToBoolean)
            .collect(Collectors.toList()));
  }

  private static Boolean convertToBoolean(final int number) {
    if (number != 1 && number != 0) {
      throw new IllegalArgumentException("사다리 입력은 0, 1 값만 유효합니다.");
    }
    return number == 1;
  }

  public static Row of(final List<Boolean> row) {
    return new Row(row);
  }

  public static Row fromGenerator(RowGenerator generator) {
    return generator.generate();
  }

  private Row(final List<Boolean> rows) {
    if (validate(rows)) {
      this.values = rows;
      return;
    }
    throw new IllegalArgumentException("잘못된 사다리 입력입니다.");
  }

  private boolean validate(final List<Boolean> row) {
    return IntStream.range(0, row.size() - 1)
            .allMatch(i -> notConsecutiveTrue(row, i));
  }

  private boolean notConsecutiveTrue(final List<Boolean> row, int i) {
    return !(row.get(i) && row.get(i + 1));
  }

  public Integer size() {
    return this.values.size();
  }

  public List<Boolean> values() {
    return this.values;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Row target = (Row) o;
    return this.values.equals(target.values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.values);
  }

  @Override
  public Iterator<Boolean> iterator() {
    return new BooleanIterator();
  }

  private class BooleanIterator implements Iterator<Boolean> {
    private int cursor = 0;

    @Override
    public boolean hasNext() {
      return cursor < values.size();
    }

    @Override
    public Boolean next() {
      return values.get(cursor++);
    }
  }
}
