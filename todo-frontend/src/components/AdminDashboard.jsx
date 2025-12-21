import React from "react";

const AdminDashboard = ({ onLogout, user }) => {
  return (
    <div className="App" style={{ maxWidth: "800px" }}>
      {/* Header sa dugmetom Odjavi se */}
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          marginBottom: "30px",
        }}
      >
        <h1 style={{ margin: 0, textAlign: "left" }}>👑 Nadzorna Plošča</h1>
        <button
          onClick={onLogout}
          style={{
            background: "#e94560",
            color: "white",
            padding: "10px 20px",
          }}
        >
          Odjavi se
        </button>
      </div>

      <p
        style={{
          textAlign: "center",
          color: "#e4e4e4",
          marginBottom: "30px",
          fontSize: "16px",
        }}
      >
        Dobrodosli, {user?.name || "Administrator"}!
      </p>

      <hr />

      {/* Dva pravougaonika */}
      <div
        style={{
          display: "grid",
          gridTemplateColumns: "1fr 1fr",
          gap: "20px",
          marginTop: "30px",
        }}
      >
        {/* Pravougaonik 1: Upravljanje uporabnikov */}
        <div
          style={{
            background: "#3c3c6e",
            borderRadius: "10px",
            padding: "30px",
            textAlign: "center",
            cursor: "pointer",
            transition: "all 0.3s ease",
            border: "2px solid #4a4a7e",
          }}
          onMouseEnter={(e) => {
            e.currentTarget.style.background = "#4a4a7e";
            e.currentTarget.style.transform = "translateY(-5px)";
          }}
          onMouseLeave={(e) => {
            e.currentTarget.style.background = "#3c3c6e";
            e.currentTarget.style.transform = "translateY(0)";
          }}
        >
          <div
            style={{
              fontSize: "48px",
              marginBottom: "15px",
            }}
          >
            👥
          </div>
          <h2
            style={{
              color: "#e94560",
              marginBottom: "10px",
              fontSize: "20px",
            }}
          >
            UPRAVLJANJE UPORABNIKOV
          </h2>
          <p
            style={{
              color: "#e4e4e4",
              fontSize: "14px",
            }}
          >
            Dodajte, uredite ali izbrišite uporabnike
          </p>
        </div>

        {/* Pravougaonik 2: Dodajanje novih kategorija */}
        <div
          style={{
            background: "#3c3c6e",
            borderRadius: "10px",
            padding: "30px",
            textAlign: "center",
            cursor: "pointer",
            transition: "all 0.3s ease",
            border: "2px solid #4a4a7e",
          }}
          onMouseEnter={(e) => {
            e.currentTarget.style.background = "#4a4a7e";
            e.currentTarget.style.transform = "translateY(-5px)";
          }}
          onMouseLeave={(e) => {
            e.currentTarget.style.background = "#3c3c6e";
            e.currentTarget.style.transform = "translateY(0)";
          }}
        >
          <div
            style={{
              fontSize: "48px",
              marginBottom: "15px",
            }}
          >
            📁
          </div>
          <h2
            style={{
              color: "#e94560",
              marginBottom: "10px",
              fontSize: "20px",
            }}
          >
            DODAJANJE NOVIH KATEGORIJ
          </h2>
          <p
            style={{
              color: "#e4e4e4",
              fontSize: "14px",
            }}
          >
            Ustvarite nove kategorije za opravila
          </p>
        </div>
      </div>

      {/* Opciono: Dodatne informacije */}
      <div
        style={{
          marginTop: "30px",
          padding: "20px",
          background: "#3c3c6e",
          borderRadius: "10px",
          textAlign: "center",
        }}
      >
        <p
          style={{
            color: "#e4e4e4",
            fontSize: "14px",
            margin: 0,
          }}
        >
          💡 Kliknite na pravokotnik za upravljanje
        </p>
      </div>
    </div>
  );
};

export default AdminDashboard;
