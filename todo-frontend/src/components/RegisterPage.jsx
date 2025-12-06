import React, { useState } from "react";

const RegisterPage = ({ onRegister, onSwitchToLogin }) => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
  });
  const [error, setError] = useState("");

  const handleChange = (field, value) => {
    setFormData({
      ...formData,
      [field]: value,
    });
  };

  const handleSubmit = async () => {
    setError("");

    if (
      !formData.name ||
      !formData.email ||
      !formData.password ||
      !formData.confirmPassword
    ) {
      setError("Molimo popunite sva polja");
      return;
    }

    if (formData.password !== formData.confirmPassword) {
      setError("Lozinke se ne podudaraju");
      return;
    }

    if (formData.password.length < 6) {
      setError("Lozinka mora imati najmanje 6 karaktera");
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/api/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          name: formData.name,
          email: formData.email,
          password: formData.password,
          s,
        }),
      });

      if (!response.ok) {
        const errorBody = await response.json().catch(() => ({}));
        const message = errorBody.message || "Greska pri registraciji";
        setError(message);
        return;
      }

      const data = await response.json();
      onRegister(data);
    } catch (err) {
      setError("Greska pri registraciji");
    }
  };

  return (
    <div
      style={{
        minHeight: "100vh",
        background: "#1a1a2e",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        padding: "40px 20px",
      }}
    >
      <div
        style={{
          background: "#2e2e4a",
          borderRadius: "16px",
          boxShadow: "0 20px 60px rgba(0,0,0,0.4)",
          padding: "40px 32px",
          width: "100%",
          maxWidth: "420px",
        }}
      >
        <div style={{ textAlign: "center", marginBottom: "30px" }}>
          <div
            style={{
              background: "linear-gradient(135deg, #e94560 0%, #ff6b6b 100%)",
              width: "80px",
              height: "80px",
              borderRadius: "50%",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              margin: "0 auto 20px",
              fontSize: "36px",
              color: "#f7fafc",
              fontWeight: 700,
              textTransform: "uppercase",
            }}
          >
            dy
          </div>
          <h1
            style={{
              fontSize: "28px",
              color: "#f7fafc",
              marginBottom: "8px",
              fontWeight: "700",
            }}
          >
            Kreirajte nalog
          </h1>
          <p style={{ color: "#cbd5e0", fontSize: "14px" }}>
            Pridruzite se i organizujte svoje zadatke
          </p>
        </div>

        {error && (
          <div
            style={{
              background: "#442b2d",
              color: "#feb2b2",
              padding: "12px",
              borderRadius: "8px",
              marginBottom: "20px",
              fontSize: "14px",
            }}
          >
            {error}
          </div>
        )}

        <div>
          <div style={{ marginBottom: "16px" }}>
            <label
              style={{
                display: "block",
                marginBottom: "8px",
                color: "#e4e4e4",
                fontSize: "14px",
                fontWeight: "600",
              }}
            >
              Ime i prezime
            </label>
            <input
              type="text"
              value={formData.name}
              onChange={(e) => handleChange("name", e.target.value)}
              placeholder="Marko Markovic"
              style={{
                width: "100%",
                padding: "12px 16px",
                border: "2px solid #3c3c6e",
                borderRadius: "8px",
                fontSize: "14px",
                transition: "border-color 0.2s",
                boxSizing: "border-box",
                background: "#1f1f39",
                color: "#f7fafc",
              }}
              onFocus={(e) => (e.target.style.borderColor = "#e94560")}
              onBlur={(e) => (e.target.style.borderColor = "#3c3c6e")}
            />
          </div>

          <div style={{ marginBottom: "16px" }}>
            <label
              style={{
                display: "block",
                marginBottom: "8px",
                color: "#e4e4e4",
                fontSize: "14px",
                fontWeight: "600",
              }}
            >
              Email adresa
            </label>
            <input
              type="email"
              value={formData.email}
              onChange={(e) => handleChange("email", e.target.value)}
              placeholder="vas.email@example.com"
              style={{
                width: "100%",
                padding: "12px 16px",
                border: "2px solid #3c3c6e",
                borderRadius: "8px",
                fontSize: "14px",
                transition: "border-color 0.2s",
                boxSizing: "border-box",
                background: "#1f1f39",
                color: "#f7fafc",
              }}
              onFocus={(e) => (e.target.style.borderColor = "#e94560")}
              onBlur={(e) => (e.target.style.borderColor = "#3c3c6e")}
            />
          </div>

          <div style={{ marginBottom: "16px" }}>
            <label
              style={{
                display: "block",
                marginBottom: "8px",
                color: "#e4e4e4",
                fontSize: "14px",
                fontWeight: "600",
              }}
            >
              Lozinka
            </label>
            <input
              type="password"
              value={formData.password}
              onChange={(e) => handleChange("password", e.target.value)}
              placeholder="********"
              style={{
                width: "100%",
                padding: "12px 16px",
                border: "2px solid #3c3c6e",
                borderRadius: "8px",
                fontSize: "14px",
                transition: "border-color 0.2s",
                boxSizing: "border-box",
                background: "#1f1f39",
                color: "#f7fafc",
              }}
              onFocus={(e) => (e.target.style.borderColor = "#e94560")}
              onBlur={(e) => (e.target.style.borderColor = "#3c3c6e")}
            />
          </div>

          <div style={{ marginBottom: "24px" }}>
            <label
              style={{
                display: "block",
                marginBottom: "8px",
                color: "#e4e4e4",
                fontSize: "14px",
                fontWeight: "600",
              }}
            >
              Potvrdite lozinku
            </label>
            <input
              type="password"
              value={formData.confirmPassword}
              onChange={(e) => handleChange("confirmPassword", e.target.value)}
              onKeyPress={(e) => e.key === "Enter" && handleSubmit()}
              placeholder="********"
              style={{
                width: "100%",
                padding: "12px 16px",
                border: "2px solid #3c3c6e",
                borderRadius: "8px",
                fontSize: "14px",
                transition: "border-color 0.2s",
                boxSizing: "border-box",
                background: "#1f1f39",
                color: "#f7fafc",
              }}
              onFocus={(e) => (e.target.style.borderColor = "#e94560")}
              onBlur={(e) => (e.target.style.borderColor = "#3c3c6e")}
            />
          </div>

          <button
            onClick={handleSubmit}
            style={{
              width: "100%",
              padding: "14px",
              background: "linear-gradient(135deg, #e94560 0%, #ff6b6b 100%)",
              color: "#f7fafc",
              border: "none",
              borderRadius: "8px",
              fontSize: "16px",
              fontWeight: "600",
              cursor: "pointer",
              transition: "transform 0.2s, box-shadow 0.2s",
              boxShadow: "0 4px 12px rgba(233, 69, 96, 0.35)",
            }}
            onMouseEnter={(e) => {
              e.target.style.transform = "translateY(-2px)";
              e.target.style.boxShadow = "0 6px 20px rgba(233, 69, 96, 0.55)";
            }}
            onMouseLeave={(e) => {
              e.target.style.transform = "translateY(0)";
              e.target.style.boxShadow = "0 4px 12px rgba(233, 69, 96, 0.35)";
            }}
          >
            Registruj se
          </button>
        </div>

        <div
          style={{
            marginTop: "24px",
            textAlign: "center",
            fontSize: "14px",
            color: "#cbd5e0",
          }}
        >
          Vec imate nalog?{" "}
          <button
            onClick={onSwitchToLogin}
            style={{
              background: "none",
              border: "none",
              color: "#e94560",
              fontWeight: "600",
              cursor: "pointer",
              textDecoration: "underline",
              padding: 0,
            }}
          >
            Prijavite se
          </button>
        </div>
      </div>
    </div>
  );
};

export default RegisterPage;
