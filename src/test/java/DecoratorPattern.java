import org.junit.Test;

import java.security.AccessControlException;

public class DecoratorPattern {
    enum Role{
        admin,
        anonymous
    }

    class User{
        private Role role;

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }
    }

    class Service{
        public void execute(String someArgument, User user){
            //Some logic
            System.out.println("----- Execute logic: " + someArgument + " -----");
        }
    }

    class AuthDecorator extends Service{
        private Service decorated;

        public AuthDecorator(Service decorated) {
            this.decorated = decorated;
        }

        @Override
        public void execute(String someArgument, User user) {
            if(user.getRole() == Role.admin){
                System.out.println("Checking role: " + user.getRole());
                decorated.execute(someArgument, user);
                System.out.println("End of Authentication");
            }else{
                throw new AccessControlException("The user is not allowed");
            }
        }
    }

    @Test
    public void TestAuth(){
        Service service = new AuthDecorator(new Service());
        User user = new User();
        user.setRole(Role.admin);
        service.execute("Hello user", user);
    }
}
