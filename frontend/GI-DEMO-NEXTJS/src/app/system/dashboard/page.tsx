import { Button } from "primereact/button";
import { InputText } from "primereact/inputtext";

export default function Dashboard() {
  return (
    <main>
      <div className="grid align-items-center p-2 w-30rem">
        <Button label="Perform" className="col-fixed"></Button>
        <InputText className="col"></InputText>
      </div>
    </main>
  );
}
