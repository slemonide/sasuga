package model;

public enum Cells {

    STATIC, RANDOM_WALK;

    public Cell getCell(Position position) {
        switch (this) {
            case STATIC:
                return new Cell(position);
            case RANDOM_WALK:
                return new RandomWalkCell(position);
            default:
                return new Cell(position);
        }
    }

    public String getName() {
        //noinspection ConstantConditions
        return getCell(Position.DEFAULT).getName();
    }
}
