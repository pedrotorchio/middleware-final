package controllers.cockpit.components;

public interface IOutputChannel {

        void write(String message);

        void writeError(String message);

}
